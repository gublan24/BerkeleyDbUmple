/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: OperationTest.java,v 1.2 2006/05/05 05:24:00 mark Exp $
 */

package com.sleepycat.persist.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.TreeMap;

import junit.framework.Test;

import com.sleepycat.collections.MapEntryParameter;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.test.TxnTestCase;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.EntityIndex;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;
import com.sleepycat.persist.raw.RawObject;
import com.sleepycat.persist.raw.RawStore;
import com.sleepycat.persist.raw.RawType;
import static com.sleepycat.persist.model.Relationship.ONE_TO_ONE;
import static com.sleepycat.persist.model.Relationship.ONE_TO_MANY;
import static com.sleepycat.persist.model.Relationship.MANY_TO_ONE;
import static com.sleepycat.persist.model.Relationship.MANY_TO_MANY;

/**
 * Tests misc store and index operations that are not tested by IndexTest.
 *
 * @author Mark Hayes
 */ 
public class OperationTest extends TxnTestCase {
 
    public static Test suite() {
        return txnTestSuite(OperationTest.class, null, null);
    }

    private EntityStore store;

    private void open()
        throws DatabaseException {

        StoreConfig config = new StoreConfig();
        config.setAllowCreate(envConfig.getAllowCreate());
        config.setTransactional(envConfig.getTransactional());

        store = new EntityStore(env, "test", config);
    }

    private void close()
        throws DatabaseException {

        store.close();
    }
    
    public void testUninitializedCursor() 
        throws DatabaseException {

        open();

        PrimaryIndex<Integer,MyEntity> priIndex =
            store.getPrimaryIndex(Integer.class, MyEntity.class);

        Transaction txn = txnBeginCursor();

        MyEntity e = new MyEntity();
        e.priKey = 1;
        e.secKey = 1;
        priIndex.put(txn, e);

        EntityCursor<MyEntity> entities = priIndex.entities(txn, null);
        try {
            entities.nextDup();
            fail();
        } catch (IllegalStateException expected) {}
        try {
            entities.prevDup();
            fail();
        } catch (IllegalStateException expected) {}
        try {
            entities.current();
            fail();
        } catch (IllegalStateException expected) {}
        try {
            entities.delete();
            fail();
        } catch (IllegalStateException expected) {}
        try {
            entities.update(e);
            fail();
        } catch (IllegalStateException expected) {}
        try {
            entities.count();
            fail();
        } catch (IllegalStateException expected) {}

        entities.close();
        txnCommit(txn);
        close();
    }
    
    public void testCursorCount() 
        throws DatabaseException {

        open();

        PrimaryIndex<Integer,MyEntity> priIndex =
            store.getPrimaryIndex(Integer.class, MyEntity.class);

        SecondaryIndex<Integer,Integer,MyEntity> secIndex =
            store.getSecondaryIndex(priIndex, Integer.class, "secKey");

        Transaction txn = txnBeginCursor();

        MyEntity e = new MyEntity();
        e.priKey = 1;
        e.secKey = 1;
        priIndex.put(txn, e);

        EntityCursor<MyEntity> cursor = secIndex.entities(txn, null);
        cursor.next();
        assertEquals(1, cursor.count());
        cursor.close();

        e.priKey = 2;
        priIndex.put(txn, e);
        cursor = secIndex.entities(txn, null);
        cursor.next();
        assertEquals(2, cursor.count());
        cursor.close();

        txnCommit(txn);
        close();
    }
    
    public void testCursorUpdate() 
        throws DatabaseException {

        open();

        PrimaryIndex<Integer,MyEntity> priIndex =
            store.getPrimaryIndex(Integer.class, MyEntity.class);

        SecondaryIndex<Integer,Integer,MyEntity> secIndex =
            store.getSecondaryIndex(priIndex, Integer.class, "secKey");

        Transaction txn = txnBeginCursor();

        Integer k;
        MyEntity e = new MyEntity();
        e.priKey = 1;
        e.secKey = 2;
        priIndex.put(txn, e);

        /* update() with primary entity cursor. */
        EntityCursor<MyEntity> entities = priIndex.entities(txn, null);
        e = entities.next();
        assertNotNull(e);
        assertEquals(1, e.priKey);
        assertEquals(Integer.valueOf(2), e.secKey);
        e.secKey = null;
        assertTrue(entities.update(e));
        e = entities.current();
        assertNotNull(e);
        assertEquals(1, e.priKey);
        assertEquals(null, e.secKey);
        e.secKey = 3;
        assertTrue(entities.update(e));
        e = entities.current();
        assertNotNull(e);
        assertEquals(1, e.priKey);
        assertEquals(Integer.valueOf(3), e.secKey);
        entities.close();

        /* update() with primary keys cursor. */
        EntityCursor<Integer> keys = priIndex.keys(txn, null);
        k = keys.next();
        assertNotNull(k);
        assertEquals(Integer.valueOf(1), k);
        try {
            keys.update(2);
            fail();
        } catch (UnsupportedOperationException expected) {
        }
        keys.close();

        /* update() with secondary entity cursor. */
        entities = secIndex.entities(txn, null);
        e = entities.next();
        assertNotNull(e);
        assertEquals(1, e.priKey);
        assertEquals(Integer.valueOf(3), e.secKey);
        try {
            entities.update(e);
            fail();
        } catch (UnsupportedOperationException expected) {
        }
        entities.close();

        /* update() with secondary keys cursor. */
        keys = secIndex.keys(txn, null);
        k = keys.next();
        assertNotNull(k);
        assertEquals(Integer.valueOf(3), k);
        try {
            keys.update(k);
            fail();
        } catch (UnsupportedOperationException expected) {
        }
        keys.close();

        txnCommit(txn);
        close();
    }
    
    public void testCursorDelete() 
        throws DatabaseException {

        open();

        PrimaryIndex<Integer,MyEntity> priIndex =
            store.getPrimaryIndex(Integer.class, MyEntity.class);

        SecondaryIndex<Integer,Integer,MyEntity> secIndex =
            store.getSecondaryIndex(priIndex, Integer.class, "secKey");

        Transaction txn = txnBeginCursor();

        /* delete() with primary and secondary entities cursor. */

        for (EntityIndex index : new EntityIndex[] { priIndex, secIndex }) {

            MyEntity e = new MyEntity();
            e.priKey = 1;
            e.secKey = 1;
            priIndex.put(txn, e);
            e.priKey = 2;
            priIndex.put(txn, e);

            EntityCursor<MyEntity> cursor = index.entities(txn, null);

            e = cursor.next();
            assertNotNull(e);
            assertEquals(1, e.priKey);
            e = cursor.current();
            assertNotNull(e);
            assertEquals(1, e.priKey);
            assertTrue(cursor.delete());
            assertTrue(!cursor.delete());
            assertNull(cursor.current());

            e = cursor.next();
            assertNotNull(e);
            assertEquals(2, e.priKey);
            e = cursor.current();
            assertNotNull(e);
            assertEquals(2, e.priKey);
            assertTrue(cursor.delete());
            assertTrue(!cursor.delete());
            assertNull(cursor.current());

            e = cursor.next();
            assertNull(e);

            if (index == priIndex) {
                e = new MyEntity();
                e.priKey = 2;
                e.secKey = 1;
                assertTrue(!cursor.update(e));
            }

            cursor.close();
        }

        /* delete() with primary and secondary keys cursor. */

        for (EntityIndex index : new EntityIndex[] { priIndex, secIndex }) {

            MyEntity e = new MyEntity();
            e.priKey = 1;
            e.secKey = 1;
            priIndex.put(txn, e);
            e.priKey = 2;
            priIndex.put(txn, e);

            EntityCursor<Integer> cursor = index.keys(txn, null);

            Integer k = cursor.next();
            assertNotNull(k);
            assertEquals(1, k.intValue());
            k = cursor.current();
            assertNotNull(k);
            assertEquals(1, k.intValue());
            assertTrue(cursor.delete());
            assertTrue(!cursor.delete());
            assertNull(cursor.current());

            int expectKey = (index == priIndex) ? 2 : 1;
            k = cursor.next();
            assertNotNull(k);
            assertEquals(expectKey, k.intValue());
            k = cursor.current();
            assertNotNull(k);
            assertEquals(expectKey, k.intValue());
            assertTrue(cursor.delete());
            assertTrue(!cursor.delete());
            assertNull(cursor.current());

            k = cursor.next();
            assertNull(k);

            cursor.close();
        }

        txnCommit(txn);
        close();
    }
    
    public void testDeleteFromSubIndex() 
        throws DatabaseException {

        open();

        PrimaryIndex<Integer,MyEntity> priIndex =
            store.getPrimaryIndex(Integer.class, MyEntity.class);

        SecondaryIndex<Integer,Integer,MyEntity> secIndex =
            store.getSecondaryIndex(priIndex, Integer.class, "secKey");

        Transaction txn = txnBegin();
        MyEntity e = new MyEntity();
        e.secKey = 1;
        e.priKey = 1;
        priIndex.put(txn, e);
        e.priKey = 2;
        priIndex.put(txn, e);
        e.priKey = 3;
        priIndex.put(txn, e);
        e.priKey = 4;
        priIndex.put(txn, e);
        txnCommit(txn);

        EntityIndex<Integer,MyEntity> subIndex = secIndex.subIndex(1);
        txn = txnBeginCursor();
        e = subIndex.get(txn, 1, null);
        assertEquals(1, e.priKey);
        assertEquals(Integer.valueOf(1), e.secKey);
        e = subIndex.get(txn, 2, null);
        assertEquals(2, e.priKey);
        assertEquals(Integer.valueOf(1), e.secKey);
        e = subIndex.get(txn, 3, null);
        assertEquals(3, e.priKey);
        assertEquals(Integer.valueOf(1), e.secKey);
        e = subIndex.get(txn, 5, null);
        assertNull(e);

        boolean deleted = subIndex.delete(txn, 1);
        assertTrue(deleted);
        assertNull(subIndex.get(txn, 1, null));
        assertNotNull(subIndex.get(txn, 2, null));

        EntityCursor<MyEntity> cursor = subIndex.entities(txn, null);
        boolean saw4 = false;
        for (MyEntity e2 = cursor.first(); e2 != null; e2 = cursor.next()) {
            if (e2.priKey == 3) {
                cursor.delete();
            }
            if (e2.priKey == 4) {
                saw4 = true;
            }
        }
        cursor.close();
        assertTrue(saw4);
        assertNull(subIndex.get(txn, 1, null));
        assertNull(subIndex.get(txn, 3, null));
        assertNotNull(subIndex.get(txn, 2, null));
        assertNotNull(subIndex.get(txn, 4, null));

        txnCommit(txn);
        close();
    }

    @Entity
    static class MyEntity {

        @PrimaryKey
        private int priKey;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Integer secKey;

        private MyEntity() {}
    }
    
    public void testSharedSequence() 
        throws DatabaseException {

        open();

        PrimaryIndex<Integer,SharedSequenceEntity1> priIndex1 =
            store.getPrimaryIndex(Integer.class, SharedSequenceEntity1.class);

        PrimaryIndex<Integer,SharedSequenceEntity2> priIndex2 =
            store.getPrimaryIndex(Integer.class, SharedSequenceEntity2.class);

        Transaction txn = txnBegin();
        SharedSequenceEntity1 e1 = new SharedSequenceEntity1();
        SharedSequenceEntity2 e2 = new SharedSequenceEntity2();
        priIndex1.put(txn, e1);
        assertEquals(1, e1.key);
        priIndex2.putNoOverwrite(txn, e2);
        assertEquals(Integer.valueOf(2), e2.key);
        e1.key = 0;
        priIndex1.putNoOverwrite(txn, e1);
        assertEquals(3, e1.key);
        e2.key = null;
        priIndex2.put(txn, e2);
        assertEquals(Integer.valueOf(4), e2.key);
        txnCommit(txn);

        close();
    }

    @Entity
    static class SharedSequenceEntity1 {

        @PrimaryKey(sequence="shared")
        private int key;
    }

    @Entity
    static class SharedSequenceEntity2 {

        @PrimaryKey(sequence="shared")
        private Integer key;
    }
    
    public void testSeparateSequence() 
        throws DatabaseException {

        open();

        PrimaryIndex<Integer,SeparateSequenceEntity1> priIndex1 =
            store.getPrimaryIndex
                (Integer.class, SeparateSequenceEntity1.class);

        PrimaryIndex<Integer,SeparateSequenceEntity2> priIndex2 =
            store.getPrimaryIndex
                (Integer.class, SeparateSequenceEntity2.class);

        Transaction txn = txnBegin();
        SeparateSequenceEntity1 e1 = new SeparateSequenceEntity1();
        SeparateSequenceEntity2 e2 = new SeparateSequenceEntity2();
        priIndex1.put(txn, e1);
        assertEquals(1, e1.key);
        priIndex2.putNoOverwrite(txn, e2);
        assertEquals(Integer.valueOf(1), e2.key);
        e1.key = 0;
        priIndex1.putNoOverwrite(txn, e1);
        assertEquals(2, e1.key);
        e2.key = null;
        priIndex2.put(txn, e2);
        assertEquals(Integer.valueOf(2), e2.key);
        txnCommit(txn);

        close();
    }

    @Entity
    static class SeparateSequenceEntity1 {

        @PrimaryKey(sequence="seq1")
        private int key;
    }

    @Entity
    static class SeparateSequenceEntity2 {

        @PrimaryKey(sequence="seq2")
        private Integer key;
    }
}
