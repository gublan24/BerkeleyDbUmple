/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *      Sleepycat Software.  All rights reserved.
 *
 * $Id: NonTxnalEntry.java,v 1.2 2006/01/03 21:56:27 bostic Exp $
 */

package com.sleepycat.je.recovery.stepwise;

import java.util.Map;
import java.util.Set;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.je.DatabaseEntry;

/*
 * A non-transactional log entry should add itself to the expected set.
 */

public class NonTxnalEntry extends LogEntryInfo {
    NonTxnalEntry(long lsn,
                  int key,
                  int data) {
        super(lsn, key, data);
    }

    /* Implement this accordingly. For example, a LogEntryInfo which
     * represents a non-txnal LN record would add that key/data to the
     * expected set. A txnal delete LN record would delete the record
     * from the expecte set at commit time.
     */
    public void updateExpectedSet(Set useExpected, Map newUncommittedRecords, Map deletedUncommittedRecords) {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        IntegerBinding.intToEntry(key, keyEntry);
        IntegerBinding.intToEntry(data, dataEntry);
        
        useExpected.add(new TestData(keyEntry, dataEntry));
    }
}
