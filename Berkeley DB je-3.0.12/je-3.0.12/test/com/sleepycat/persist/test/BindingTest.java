/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002-2006
 *	Sleepycat Software.  All rights reserved.
 *
 * $Id: BindingTest.java,v 1.16 2006/05/24 17:13:33 mark Exp $
 */

package com.sleepycat.persist.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.sleepycat.bind.EntityBinding;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.ForeignMultiKeyNullifier;
import com.sleepycat.je.SecondaryKeyCreator;
import com.sleepycat.je.SecondaryMultiKeyCreator;
import com.sleepycat.je.util.TestUtils;
import com.sleepycat.persist.impl.PersistCatalog;
import com.sleepycat.persist.impl.PersistComparator;
import com.sleepycat.persist.impl.PersistEntityBinding;
import com.sleepycat.persist.impl.PersistKeyBinding;
import com.sleepycat.persist.impl.PersistKeyCreator;
import com.sleepycat.persist.impl.SimpleCatalog;
import com.sleepycat.persist.model.AnnotationModel;
import com.sleepycat.persist.model.ClassMetadata;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.EntityMetadata;
import com.sleepycat.persist.model.EntityModel;
import com.sleepycat.persist.model.KeyField;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PersistentProxy;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.PrimaryKeyMetadata;
import com.sleepycat.persist.model.SecondaryKey;
import com.sleepycat.persist.model.SecondaryKeyMetadata;
import com.sleepycat.persist.raw.RawField;
import com.sleepycat.persist.raw.RawObject;
import com.sleepycat.persist.raw.RawType;

import static com.sleepycat.persist.model.Relationship.ONE_TO_ONE;
import static com.sleepycat.persist.model.Relationship.ONE_TO_MANY;
import static com.sleepycat.persist.model.Relationship.MANY_TO_ONE;

/**
 * @author Mark Hayes
 */
public class BindingTest extends TestCase {

    private File envHome;
    private Environment env;
    private EntityModel model;
    private Database catalogDb;
    private PersistCatalog catalog;
    private DatabaseEntry keyEntry;
    private DatabaseEntry dataEntry;

    public void setUp()
        throws IOException {

        envHome = new File(System.getProperty(TestUtils.DEST_DIR));
        keyEntry = new DatabaseEntry();
        dataEntry = new DatabaseEntry();
        TestUtils.removeLogFiles("Setup", envHome, false);
    }

    public void tearDown()
        throws IOException {

        if (env != null) {
            try {
                env.close();
            } catch (DatabaseException e) {
                System.out.println("During tearDown: " + e);
            }
        }
        try {
            TestUtils.removeLogFiles("TearDown", envHome, false);
        } catch (Error e) {
            System.out.println("During tearDown: " + e);
        }
        envHome = null;
        env = null;
        catalogDb = null;
        catalog = null;
        keyEntry = null;
        dataEntry = null;
    }

    private void open()
        throws DatabaseException {

        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        env = new Environment(envHome, envConfig);

        openCatalog();
    }

    private void openCatalog()
        throws DatabaseException {

        model = new AnnotationModel();
        model.registerClass(LocalizedTextProxy.class);
        model.registerClass(LocaleProxy.class);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        catalog = new PersistCatalog
            (null, env, "catalog", dbConfig, model, null,
             true /*useCurrentModel*/);
    }

    private void close()
        throws DatabaseException {

        /* Close/open/close catalog to test checks for class evolution. */
        catalog.close();
        openCatalog();
        catalog.close();
        catalog = null;

        env.close();
        env = null;
    }

    public void testBasic()
        throws DatabaseException {

        open();

        checkEntity(Basic.class,
                    new Basic(1, "one", 2.2, "three"));
        checkEntity(Basic.class,
                    new Basic(0, null, 0, null));
        checkEntity(Basic.class,
                    new Basic(-1, "xxx", -2, "xxx"));

        checkMetadata(Basic.class.getName(), new String[][] {
                          {"id", "long"},
                          {"one", "java.lang.String"},
                          {"two", "double"},
                          {"three", "java.lang.String"},
                      },
                      0 /*priKeyIndex*/, null);

        close();
    }

    @Entity
    static class Basic implements MyEntity {

        @PrimaryKey
        private long id;
        private String one;
        private double two;
        private String three;

        private Basic() { }

        private Basic(long id, String one, double two, String three) {
            this.id = id;
            this.one = one;
            this.two = two;
            this.three = three;
        }

        public String getBasicOne() {
            return one;
        }

        public Object getPriKeyObject() {
            return id;
        }

        public void validate(Object other) {
            Basic o = (Basic) other;
            TestCase.assertEquals(id, o.id);
            TestCase.assertTrue(nullOrEqual(one, o.one));
            TestCase.assertEquals(two, o.two);
            TestCase.assertTrue(nullOrEqual(three, o.three));
            if (one == three) {
                TestCase.assertSame(o.one, o.three);
            }
        }

        @Override
        public String toString() {
            return "" + id + ' ' + one + ' ' + two;
        }
    }

    public void testSimpleTypes()
        throws DatabaseException {

        open();

        checkEntity(SimpleTypes.class, new SimpleTypes());

        checkMetadata(SimpleTypes.class.getName(), new String[][] {
                          {"f0", "boolean"},
                          {"f1", "char"},
                          {"f2", "byte"},
                          {"f3", "short"},
                          {"f4", "int"},
                          {"f5", "long"},
                          {"f6", "float"},
                          {"f7", "double"},
                          {"f8", "java.lang.String"},
                          //{"f9", "java.math.BigInteger"},
                          //{"f10", "java.math.BigDecimal"},
                          {"f11", "java.util.Date"},
                          {"f12", "java.lang.Boolean"},
                          {"f13", "java.lang.Character"},
                          {"f14", "java.lang.Byte"},
                          {"f15", "java.lang.Short"},
                          {"f16", "java.lang.Integer"},
                          {"f17", "java.lang.Long"},
                          {"f18", "java.lang.Float"},
                          {"f19", "java.lang.Double"},
                      },
                      0 /*priKeyIndex*/, null);

        close();
    }

    @Entity
    static class SimpleTypes implements MyEntity {

        @PrimaryKey
        private boolean f0 = true;
        private char f1 = 'a';
        private byte f2 = 123;
        private short f3 = 123;
        private int f4 = 123;
        private long f5 = 123;
        private float f6 = 123.4f;
        private double f7 = 123.4;
        private String f8 = "xxx";
        //private BigInteger f9 = BigInteger.valueOf(123);
        //private BigDecimal f10 = BigDecimal.valueOf(123.4);
        private Date f11 = new Date();
        private Boolean f12 = true;
        private Character f13 = 'a';
        private Byte f14 = 123;
        private Short f15 = 123;
        private Integer f16 = 123;
        private Long f17 = 123L;
        private Float f18 = 123.4f;
        private Double f19 = 123.4;

        SimpleTypes() { }

        public Object getPriKeyObject() {
            return f0;
        }

        public void validate(Object other) {
            SimpleTypes o = (SimpleTypes) other;
            TestCase.assertEquals(f0, o.f0);
            TestCase.assertEquals(f1, o.f1);
            TestCase.assertEquals(f2, o.f2);
            TestCase.assertEquals(f3, o.f3);
            TestCase.assertEquals(f4, o.f4);
            TestCase.assertEquals(f5, o.f5);
            TestCase.assertEquals(f6, o.f6);
            TestCase.assertEquals(f7, o.f7);
            TestCase.assertEquals(f8, o.f8);
            //TestCase.assertEquals(f9, o.f9);
            //TestCase.assertEquals(f10, o.f10);
            TestCase.assertEquals(f11, o.f11);
            TestCase.assertEquals(f12, o.f12);
            TestCase.assertEquals(f13, o.f13);
            TestCase.assertEquals(f14, o.f14);
            TestCase.assertEquals(f15, o.f15);
            TestCase.assertEquals(f16, o.f16);
            TestCase.assertEquals(f17, o.f17);
            TestCase.assertEquals(f18, o.f18);
            TestCase.assertEquals(f19, o.f19);
        }
    }

    public void testArrayTypes()
        throws DatabaseException {

        open();

        checkEntity(ArrayTypes.class, new ArrayTypes());

        checkMetadata(ArrayTypes.class.getName(), new String[][] {
                          {"id", "int"},
                          {"f0", boolean[].class.getName()},
                          {"f1", char[].class.getName()},
                          {"f2", byte[].class.getName()},
                          {"f3", short[].class.getName()},
                          {"f4", int[].class.getName()},
                          {"f5", long[].class.getName()},
                          {"f6", float[].class.getName()},
                          {"f7", double[].class.getName()},
                          {"f8", String[].class.getName()},
                          {"f9", Address[].class.getName()},
                          {"f10", boolean[][][].class.getName()},
                          {"f11", String[][][].class.getName()},
                      },
                      0 /*priKeyIndex*/, null);

        close();
    }

    @Entity
    static class ArrayTypes implements MyEntity {

        @PrimaryKey
        private int id = 1;
        private boolean[] f0 =  {false, true};
        private char[] f1 = {'a', 'b'};
        private byte[] f2 = {1, 2};
        private short[] f3 = {1, 2};
        private int[] f4 = {1, 2};
        private long[] f5 = {1, 2};
        private float[] f6 = {1.1f, 2.2f};
        private double[] f7 = {1.1, 2,2};
        private String[] f8 = {"xxx", null, "yyy"};
        private Address[] f9 = {new Address("city", "state", 123),
                                null,
                                new Address("x", "y", 444)};
        private boolean[][][] f10 = 
        {
            {
                {false, true},
                {false, true},
            },
            null,
            {
                {false, true},
                {false, true},
            },
        };
        private String[][][] f11 = 
        {
            {
                {"xxx", null, "yyy"},
                null,
                {"xxx", null, "yyy"},
            },
            null,
            {
                {"xxx", null, "yyy"},
                null,
                {"xxx", null, "yyy"},
            },
        };

        ArrayTypes() { }

        public Object getPriKeyObject() {
            return id;
        }

        public void validate(Object other) {
            ArrayTypes o = (ArrayTypes) other;
            TestCase.assertEquals(id, o.id);
            TestCase.assertTrue(Arrays.equals(f0, o.f0));
            TestCase.assertTrue(Arrays.equals(f1, o.f1));
            TestCase.assertTrue(Arrays.equals(f2, o.f2));
            TestCase.assertTrue(Arrays.equals(f3, o.f3));
            TestCase.assertTrue(Arrays.equals(f4, o.f4));
            TestCase.assertTrue(Arrays.equals(f5, o.f5));
            TestCase.assertTrue(Arrays.equals(f6, o.f6));
            TestCase.assertTrue(Arrays.equals(f7, o.f7));
            TestCase.assertTrue(Arrays.equals(f8, o.f8));
            TestCase.assertTrue(Arrays.deepEquals(f9, o.f9));
            TestCase.assertTrue(Arrays.deepEquals(f10, o.f10));
            TestCase.assertTrue(Arrays.deepEquals(f11, o.f11));
        }
    }

    public void testEnumTypes()
        throws DatabaseException {

        open();

        checkEntity(EnumTypes.class, new EnumTypes());

        checkMetadata(EnumTypes.class.getName(), new String[][] {
                          {"f0", "int"},
                          {"f1", Thread.State.class.getName()},
                          {"f2", EnumTypes.MyEnum.class.getName()},
                          {"f3", Object.class.getName()},
                      },
                      0 /*priKeyIndex*/, null);

        close();
    }

    @Entity
    static class EnumTypes implements MyEntity {

        private static enum MyEnum { ONE, TWO };

        @PrimaryKey
        private int f0 = 1;
        private Thread.State f1 = Thread.State.RUNNABLE;
        private MyEnum f2 = MyEnum.ONE;
        private Object f3 = MyEnum.TWO;

        EnumTypes() { }

        public Object getPriKeyObject() {
            return f0;
        }

        public void validate(Object other) {
            EnumTypes o = (EnumTypes) other;
            TestCase.assertEquals(f0, o.f0);
            TestCase.assertSame(f1, o.f1);
            TestCase.assertSame(f2, o.f2);
            TestCase.assertSame(f3, o.f3);
        }
    }

    public void testProxyTypes()
        throws DatabaseException {

        open();

        checkEntity(ProxyTypes.class, new ProxyTypes());

        checkMetadata(ProxyTypes.class.getName(), new String[][] {
                          {"f0", "int"},
                          {"f1", Locale.class.getName()},
                          {"f2", Set.class.getName()},
                          {"f3", Set.class.getName()},
                          {"f4", Object.class.getName()},
                          {"f5", HashMap.class.getName()},
                          {"f6", TreeMap.class.getName()},
                          {"f7", List.class.getName()},
                          {"f8", LinkedList.class.getName()},
                          {"f9", LocalizedText.class.getName()},
                      },
                      0 /*priKeyIndex*/, null);

        close();
    }

    @Entity
    static class ProxyTypes implements MyEntity {

        @PrimaryKey
        private int f0 = 1;
        private Locale f1 = Locale.getDefault();
        private Set<Integer> f2 = new HashSet<Integer>();
        private Set<Integer> f3 = new TreeSet<Integer>();
        private Object f4 = new HashSet<Address>();
        private HashMap<String,Integer> f5 = new HashMap<String,Integer>();
        private TreeMap<String,Address> f6 = new TreeMap<String,Address>();
        private List<Integer> f7 = new ArrayList<Integer>();
        private LinkedList<Integer> f8 = new LinkedList<Integer>();
        private LocalizedText f9 = new LocalizedText(f1, "xyz");

        ProxyTypes() {
            f2.add(123);
            f2.add(456);
            f3.add(456);
            f3.add(123);
            HashSet<Address> s = (HashSet) f4;
            s.add(new Address("city", "state", 11111));
            s.add(new Address("city2", "state2", 22222));
            s.add(new Address("city3", "state3", 33333));
            f5.put("one", 111);
            f5.put("two", 222);
            f5.put("three", 333);
            f6.put("one", new Address("city", "state", 11111));
            f6.put("two", new Address("city2", "state2", 22222));
            f6.put("three", new Address("city3", "state3", 33333));
            f7.add(123);
            f7.add(456);
            f8.add(123);
            f8.add(456);
        }

        public Object getPriKeyObject() {
            return f0;
        }

        public void validate(Object other) {
            ProxyTypes o = (ProxyTypes) other;
            TestCase.assertEquals(f0, o.f0);
            TestCase.assertEquals(f1, o.f1);
            TestCase.assertEquals(f2, o.f2);
            TestCase.assertEquals(f3, o.f3);
            TestCase.assertEquals(f4, o.f4);
            TestCase.assertEquals(f5, o.f5);
            TestCase.assertEquals(f6, o.f6);
            TestCase.assertEquals(f7, o.f7);
            TestCase.assertEquals(f8, o.f8);
            TestCase.assertEquals(f9, o.f9);
        }
    }

    @Persistent(proxyFor=Locale.class)
    static class LocaleProxy implements PersistentProxy<Locale> {

        String language;
        String country;
        String variant;

        private LocaleProxy() {}

        public void initializeProxy(Locale object) {
            language = object.getLanguage();
            country = object.getCountry();
            variant = object.getVariant();
        }

        public Locale convertProxy() {
            return new Locale(language, country, variant);
        }
    }

    static class LocalizedText {

        Locale locale;
        String text;

        LocalizedText(Locale locale, String text) {
            this.locale = locale;
            this.text = text;
        }

        @Override
        public boolean equals(Object other) {
            LocalizedText o = (LocalizedText) other;
            return text.equals(o.text) &&
                   locale.equals(o.locale);
        }
    }

    @Persistent(proxyFor=LocalizedText.class)
    static class LocalizedTextProxy implements PersistentProxy<LocalizedText> {

        Locale locale;
        String text;

        private LocalizedTextProxy() {}

        public void initializeProxy(LocalizedText object) {
            locale = object.locale;
            text = object.text;
        }

        public LocalizedText convertProxy() {
            return new LocalizedText(locale, text);
        }
    }

    public void testEmbedded()
        throws DatabaseException {

        open();

        Address a1 = new Address("city", "state", 123);
        Address a2 = new Address("Wikieup", "AZ", 85360);

        checkEntity(Embedded.class,
                    new Embedded("x", a1, a2));
        checkEntity(Embedded.class,
                    new Embedded("y", a1, null));
        checkEntity(Embedded.class,
                    new Embedded("", a2, a2));

        checkMetadata(Embedded.class.getName(), new String[][] {
                        {"id", "java.lang.String"},
                        {"idShadow", "java.lang.String"},
                        {"one", Address.class.getName()},
                        {"two", Address.class.getName()},
                      },
                      0 /*priKeyIndex*/, null);

        checkMetadata(Address.class.getName(), new String[][] {
                        {"street", "java.lang.String"},
                        {"city", "java.lang.String"},
                        {"zip", "int"},
                      },
                      -1 /*priKeyIndex*/, null);

        close();
    }

    @Entity
    static class Embedded implements MyEntity {

        @PrimaryKey
        private String id;
        private String idShadow;
        private Address one;
        private Address two;

        private Embedded() { }

        private Embedded(String id, Address one, Address two) {
            this.id = id;
            idShadow = id;
            this.one = one;
            this.two = two;
        }

        public Object getPriKeyObject() {
            return id;
        }

        public void validate(Object other) {
            Embedded o = (Embedded) other;
            TestCase.assertEquals(id, o.id);
            if (one != null) {
                one.validate(o.one);
            } else {
                assertNull(o.one);
            }
            if (two != null) {
                two.validate(o.two);
            } else {
                assertNull(o.two);
            }
            TestCase.assertSame(o.id, o.idShadow);
            if (one == two) {
                TestCase.assertSame(o.one, o.two);
            }
        }

        @Override
        public String toString() {
            return "" + id + ' ' + one + ' ' + two;
        }
    }

    @Persistent
    static class Address {

        private String street;
        private String city;
        private int zip;

        private Address() {}

        Address(String street, String city, int zip) {
            this.street = street;
            this.city = city;
            this.zip = zip;
        }

        void validate(Address o) {
            TestCase.assertTrue(nullOrEqual(street, o.street));
            TestCase.assertTrue(nullOrEqual(city, o.city));
            TestCase.assertEquals(zip, o.zip);
        }

        @Override
        public String toString() {
            return "" + street + ' ' + city + ' ' + zip;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            Address o = (Address) other;
            return nullOrEqual(street, o.street) &&
                   nullOrEqual(city, o.city) &&
                   nullOrEqual(zip, o.zip);
        }

        @Override
        public int hashCode() {
            return zip;
        }
    }

    public void testSubclass()
        throws DatabaseException {

        open();

        checkEntity(Basic.class,
                    new Subclass(-1, "xxx", -2, "xxx", "xxx", true));

        checkMetadata(Basic.class.getName(), new String[][] {
                          {"id", "long"},
                          {"one", "java.lang.String"},
                          {"two", "double"},
                          {"three", "java.lang.String"},
                      },
                      0 /*priKeyIndex*/, null);
        checkMetadata(Subclass.class.getName(), new String[][] {
                          {"one", "java.lang.String"},
                          {"two", "boolean"},
                      },
                      -1 /*priKeyIndex*/, Basic.class.getName());

        close();
    }

    @Persistent
    static class Subclass extends Basic {

        private String one;
        private boolean two;

	private Subclass() {
	}

        private Subclass(long id, String one, double two, String three,
                         String subOne, boolean subTwo) {
            super(id, one, two, three);
            this.one = subOne;
            this.two = subTwo;
	}

        public void validate(Object other) {
            super.validate(other);
            Subclass o = (Subclass) other;
            TestCase.assertTrue(nullOrEqual(one, o.one));
            TestCase.assertEquals(two, o.two);
            if (one == getBasicOne()) {
                TestCase.assertSame(o.one, o.getBasicOne());
            }
        }
    }

    public void testSuperclass()
        throws DatabaseException {

        open();

        checkEntity(UseSuperclass.class,
                    new UseSuperclass(33, "xxx"));

        checkMetadata(Superclass.class.getName(), new String[][] {
                          {"id", "int"},
                          {"one", "java.lang.String"},
                      },
                      0 /*priKeyIndex*/, null);
        checkMetadata(UseSuperclass.class.getName(), new String[][] {
                      },
                      -1 /*priKeyIndex*/, Superclass.class.getName());

        close();
    }

    @Persistent
    static class Superclass implements MyEntity {

        @PrimaryKey
        private int id;
        private String one;

        private Superclass() { }

        private Superclass(int id, String one) {
            this.id = id;
            this.one = one;
        }

        public Object getPriKeyObject() {
            return id;
        }

        public void validate(Object other) {
            Superclass o = (Superclass) other;
            TestCase.assertEquals(id, o.id);
            TestCase.assertTrue(nullOrEqual(one, o.one));
        }
    }

    @Entity
    static class UseSuperclass extends Superclass {

        private UseSuperclass() { }

        private UseSuperclass(int id, String one) {
            super(id, one);
        }
    }

    public void testAbstract()
        throws DatabaseException {

        open();

        checkEntity(EntityUseAbstract.class,
                    new EntityUseAbstract(33, "xxx"));

        checkMetadata(Abstract.class.getName(), new String[][] {
                          {"one", "java.lang.String"},
                      },
                      -1 /*priKeyIndex*/, null);
        checkMetadata(EmbeddedUseAbstract.class.getName(), new String[][] {
                          {"two", "java.lang.String"},
                      },
                      -1 /*priKeyIndex*/, Abstract.class.getName());
        checkMetadata(EntityUseAbstract.class.getName(), new String[][] {
                          {"id", "int"},
                          {"f1", EmbeddedUseAbstract.class.getName()},
                          {"f2", Abstract.class.getName()},
                          {"f3", Object.class.getName()},
                          {"f4", Interface.class.getName()},
                          {"a1", EmbeddedUseAbstract[].class.getName()},
                          {"a2", Abstract[].class.getName()},
                          {"a3", Abstract[].class.getName()},
                          {"a4", Object[].class.getName()},
                          {"a5", Interface[].class.getName()},
                          {"a6", Interface[].class.getName()},
                          {"a7", Interface[].class.getName()},
                      },
                      0 /*priKeyIndex*/, Abstract.class.getName());

        close();
    }

    @Persistent
    static abstract class Abstract implements Interface {

        String one;

        private Abstract() { }

        private Abstract(String one) {
            this.one = one;
        }

        public void validate(Object other) {
            Abstract o = (Abstract) other;
            TestCase.assertTrue(nullOrEqual(one, o.one));
        }

        @Override
        public boolean equals(Object other) {
            Abstract o = (Abstract) other;
            return nullOrEqual(one, o.one);
        }
    }

    interface Interface {
        void validate(Object other);
    }

    @Persistent
    static class EmbeddedUseAbstract extends Abstract {

        private String two;

        private EmbeddedUseAbstract() { }

        private EmbeddedUseAbstract(String one, String two) {
            super(one);
            this.two = two;
        }

        @Override
        public void validate(Object other) {
            super.validate(other);
            EmbeddedUseAbstract o = (EmbeddedUseAbstract) other;
            TestCase.assertTrue(nullOrEqual(two, o.two));
        }

        @Override
        public boolean equals(Object other) {
            if (!super.equals(other)) {
                return false;
            }
            EmbeddedUseAbstract o = (EmbeddedUseAbstract) other;
            return nullOrEqual(two, o.two);
        }
    }

    @Entity
    static class EntityUseAbstract extends Abstract implements MyEntity {

        @PrimaryKey
        private int id;

        private EmbeddedUseAbstract f1;
        private Abstract f2;
        private Object f3;
        private Interface f4;
        private EmbeddedUseAbstract[] a1;
        private Abstract[] a2;
        private Abstract[] a3;
        private Object[] a4;
        private Interface[] a5;
        private Interface[] a6;
        private Interface[] a7;

        private EntityUseAbstract() { }

        private EntityUseAbstract(int id, String one) {
            super(one);
            this.id = id;
            f1 = new EmbeddedUseAbstract(one, one);
            f2 = new EmbeddedUseAbstract(one + "x", one + "y");
            f3 = new EmbeddedUseAbstract(null, null);
            f4 = new EmbeddedUseAbstract(null, null);
            a1 = new EmbeddedUseAbstract[3];
            a2 = new EmbeddedUseAbstract[3];
            a3 = new Abstract[3];
            a4 = new Object[3];
            a5 = new EmbeddedUseAbstract[3];
            a6 = new Abstract[3];
            a7 = new Interface[3];
            for (int i = 0; i < 3; i += 1) {
                a1[i] = new EmbeddedUseAbstract("1" + i, null);
                a2[i] = new EmbeddedUseAbstract("2" + i, null);
                a3[i] = new EmbeddedUseAbstract("3" + i, null);
                a4[i] = new EmbeddedUseAbstract("4" + i, null);
                a5[i] = new EmbeddedUseAbstract("5" + i, null);
                a6[i] = new EmbeddedUseAbstract("6" + i, null);
                a7[i] = new EmbeddedUseAbstract("7" + i, null);
            }
        }

        public Object getPriKeyObject() {
            return id;
        }

        @Override
        public void validate(Object other) {
            super.validate(other);
            EntityUseAbstract o = (EntityUseAbstract) other;
            TestCase.assertEquals(id, o.id);
            f1.validate(o.f1);
            assertSame(o.one, o.f1.one);
            assertSame(o.f1.one, o.f1.two);
            f2.validate(o.f2);
            ((Abstract) f3).validate(o.f3);
            f4.validate(o.f4);
            assertTrue(arrayToString(a1) + ' ' + arrayToString(o.a1),
                       Arrays.equals(a1, o.a1));
            assertTrue(Arrays.equals(a2, o.a2));
            assertTrue(Arrays.equals(a3, o.a3));
            assertTrue(Arrays.equals(a4, o.a4));
            assertTrue(Arrays.equals(a5, o.a5));
            assertTrue(Arrays.equals(a6, o.a6));
            assertTrue(Arrays.equals(a7, o.a7));
            assertSame(EmbeddedUseAbstract.class, f2.getClass());
            assertSame(EmbeddedUseAbstract.class, f3.getClass());
            assertSame(EmbeddedUseAbstract[].class, a1.getClass());
            assertSame(EmbeddedUseAbstract[].class, a2.getClass());
            assertSame(Abstract[].class, a3.getClass());
            assertSame(Object[].class, a4.getClass());
            assertSame(EmbeddedUseAbstract[].class, a5.getClass());
            assertSame(Abstract[].class, a6.getClass());
            assertSame(Interface[].class, a7.getClass());
        }
    }

    public void testCompositeKey()
        throws DatabaseException {

        open();

        CompositeKey key = new CompositeKey(123, 456L, "xyz");
        checkEntity(UseCompositeKey.class,
                    new UseCompositeKey(key, "one"));

        checkMetadata(UseCompositeKey.class.getName(), new String[][] {
                          {"key", CompositeKey.class.getName()},
                          {"one", "java.lang.String"},
                      },
                      0 /*priKeyIndex*/, null);

        checkMetadata(CompositeKey.class.getName(), new String[][] {
                        {"f1", "int"},
                        {"f2", "java.lang.Long"},
                        {"f3", "java.lang.String"},
                      },
                      -1 /*priKeyIndex*/, null);

        close();
    }

    @Persistent
    static class CompositeKey {
        @KeyField(3)
        private int f1;
        @KeyField(2)
        private Long f2;
        @KeyField(1)
        private String f3;

        private CompositeKey() {}

        CompositeKey(int f1, Long f2, String f3) {
            this.f1 = f1;
            this.f2 = f2;
            this.f3 = f3;
        }

        void validate(CompositeKey o) {
            TestCase.assertEquals(f1, o.f1);
            TestCase.assertTrue(nullOrEqual(f2, o.f2));
            TestCase.assertTrue(nullOrEqual(f3, o.f3));
        }

        @Override
        public boolean equals(Object other) {
            CompositeKey o = (CompositeKey) other;
            return f1 == o.f1 &&
                   nullOrEqual(f2, o.f2) &&
                   nullOrEqual(f3, o.f3);
        }

        @Override
        public int hashCode() {
            return f1;
        }

        @Override
        public String toString() {
            return "" + f1 + ' ' + f2 + ' ' + f3;
        }
    }

    @Entity
    static class UseCompositeKey implements MyEntity {

        @PrimaryKey
        private CompositeKey key;
        private String one;

        private UseCompositeKey() { }

        private UseCompositeKey(CompositeKey key, String one) {
            this.key = key;
            this.one = one;
        }

        public Object getPriKeyObject() {
            return key;
        }

        public void validate(Object other) {
            UseCompositeKey o = (UseCompositeKey) other;
            TestCase.assertNotNull(key);
            TestCase.assertNotNull(o.key);
            key.validate(o.key);
            TestCase.assertTrue(nullOrEqual(one, o.one));
        }
    }

    public void testComparableKey()
        throws DatabaseException {

        open();

        ComparableKey key = new ComparableKey(123, 456);
        checkEntity(UseComparableKey.class,
                    new UseComparableKey(key, "one"));

        checkMetadata(UseComparableKey.class.getName(), new String[][] {
                          {"key", ComparableKey.class.getName()},
                          {"one", "java.lang.String"},
                      },
                      0 /*priKeyIndex*/, null);

        checkMetadata(ComparableKey.class.getName(), new String[][] {
                        {"f1", "int"},
                        {"f2", "int"},
                      },
                      -1 /*priKeyIndex*/, null);

        ClassMetadata classMeta =
            model.getClassMetadata(UseComparableKey.class.getName());
        assertNotNull(classMeta);

        PersistKeyBinding binding = new PersistKeyBinding
            (catalog, ComparableKey.class.getName(), false);

        PersistComparator comparator = new PersistComparator
            (ComparableKey.class.getName(),
             classMeta.getCompositeKeyFields(),
             binding);

        compareKeys(comparator, binding, new ComparableKey(1, 1),
                                         new ComparableKey(1, 1), 0);
        compareKeys(comparator, binding, new ComparableKey(1, 2),
                                         new ComparableKey(1, 1), -1);
        compareKeys(comparator, binding, new ComparableKey(2, 1),
                                         new ComparableKey(1, 1), -1);
        compareKeys(comparator, binding, new ComparableKey(2, 1),
                                         new ComparableKey(3, 1), 1);

        close();
    }

    private void compareKeys(Comparator<Object> comparator,
                             EntryBinding binding,
                             Object key1,
                             Object key2,
                             int expectResult) {
        DatabaseEntry entry1 = new DatabaseEntry();
        DatabaseEntry entry2 = new DatabaseEntry();
        binding.objectToEntry(key1, entry1);
        binding.objectToEntry(key2, entry2);
        int result = comparator.compare(entry1.getData(), entry2.getData());
        assertEquals(expectResult, result);
    }

    @Persistent
    static class ComparableKey implements Comparable<ComparableKey> {
        @KeyField(2)
        private int f1;
        @KeyField(1)
        private int f2;

        private ComparableKey() {}

        ComparableKey(int f1, int f2) {
            this.f1 = f1;
            this.f2 = f2;
        }

        void validate(ComparableKey o) {
            TestCase.assertEquals(f1, o.f1);
            TestCase.assertEquals(f2, o.f2);
        }

        @Override
        public boolean equals(Object other) {
            ComparableKey o = (ComparableKey) other;
            return f1 == o.f1 && f2 == o.f2;
        }

        @Override
        public int hashCode() {
            return f1 + f2;
        }

        @Override
        public String toString() {
            return "" + f1 + ' ' + f2;
        }

        /** Compare f1 then f2, in reverse integer order. */
        public int compareTo(ComparableKey o) {
            if (f1 != o.f1) {
                return o.f1 - f1;
            } else {
                return o.f2 - f2;
            }
        }
    }

    @Entity
    static class UseComparableKey implements MyEntity {

        @PrimaryKey
        private ComparableKey key;
        private String one;

        private UseComparableKey() { }

        private UseComparableKey(ComparableKey key, String one) {
            this.key = key;
            this.one = one;
        }

        public Object getPriKeyObject() {
            return key;
        }

        public void validate(Object other) {
            UseComparableKey o = (UseComparableKey) other;
            TestCase.assertNotNull(key);
            TestCase.assertNotNull(o.key);
            key.validate(o.key);
            TestCase.assertTrue(nullOrEqual(one, o.one));
        }
    }

    public void testSecKeys()
        throws DatabaseException {

        open();

        SecKeys obj = new SecKeys();
        checkEntity(SecKeys.class, obj);

        checkMetadata(SecKeys.class.getName(), new String[][] {
                          {"id", "long"},
                          {"f0", "boolean"},
                          {"g0", "boolean"},
                          {"f1", "char"},
                          {"g1", "char"},
                          {"f2", "byte"},
                          {"g2", "byte"},
                          {"f3", "short"},
                          {"g3", "short"},
                          {"f4", "int"},
                          {"g4", "int"},
                          {"f5", "long"},
                          {"g5", "long"},
                          {"f6", "float"},
                          {"g6", "float"},
                          {"f7", "double"},
                          {"g7", "double"},
                          {"f8", "java.lang.String"},
                          {"g8", "java.lang.String"},
                          //{"f9", "java.math.BigInteger"},
                          //{"g9", "java.math.BigInteger"},
                          //{"f10", "java.math.BigDecimal"},
                          //{"g10", "java.math.BigDecimal"},
                          {"f11", "java.util.Date"},
                          {"g11", "java.util.Date"},
                          {"f12", "java.lang.Boolean"},
                          {"g12", "java.lang.Boolean"},
                          {"f13", "java.lang.Character"},
                          {"g13", "java.lang.Character"},
                          {"f14", "java.lang.Byte"},
                          {"g14", "java.lang.Byte"},
                          {"f15", "java.lang.Short"},
                          {"g15", "java.lang.Short"},
                          {"f16", "java.lang.Integer"},
                          {"g16", "java.lang.Integer"},
                          {"f17", "java.lang.Long"},
                          {"g17", "java.lang.Long"},
                          {"f18", "java.lang.Float"},
                          {"g18", "java.lang.Float"},
                          {"f19", "java.lang.Double"},
                          {"g19", "java.lang.Double"},
                          {"f20", CompositeKey.class.getName()},
                          {"g20", CompositeKey.class.getName()},
                          {"f21", int[].class.getName()},
                          {"g21", int[].class.getName()},
                          {"f22", Integer[].class.getName()},
                          {"g22", Integer[].class.getName()},
                          {"f23", Set.class.getName()},
                          {"g23", Set.class.getName()},
                          {"f24", CompositeKey[].class.getName()},
                          {"g24", CompositeKey[].class.getName()},
                          {"f25", Set.class.getName()},
                          {"g25", Set.class.getName()},
                          {"f31", "java.util.Date"},
                          {"f32", "java.lang.Boolean"},
                          {"f33", "java.lang.Character"},
                          {"f34", "java.lang.Byte"},
                          {"f35", "java.lang.Short"},
                          {"f36", "java.lang.Integer"},
                          {"f37", "java.lang.Long"},
                          {"f38", "java.lang.Float"},
                          {"f39", "java.lang.Double"},
                          {"f40", CompositeKey.class.getName()},
                      },
                      0 /*priKeyIndex*/, null);

        checkSecKey(obj, "f0", obj.f0, Boolean.class);
        checkSecKey(obj, "f1", obj.f1, Character.class);
        checkSecKey(obj, "f2", obj.f2, Byte.class);
        checkSecKey(obj, "f3", obj.f3, Short.class);
        checkSecKey(obj, "f4", obj.f4, Integer.class);
        checkSecKey(obj, "f5", obj.f5, Long.class);
        checkSecKey(obj, "f6", obj.f6, Float.class);
        checkSecKey(obj, "f7", obj.f7, Double.class);
        checkSecKey(obj, "f8", obj.f8, String.class);
        //checkSecKey(obj, "f9", obj.f9, BigInteger.class);
        //checkSecKey(obj, "f10", obj.f10, BigDecimal.class);
        checkSecKey(obj, "f11", obj.f11, Date.class);
        checkSecKey(obj, "f12", obj.f12, Boolean.class);
        checkSecKey(obj, "f13", obj.f13, Character.class);
        checkSecKey(obj, "f14", obj.f14, Byte.class);
        checkSecKey(obj, "f15", obj.f15, Short.class);
        checkSecKey(obj, "f16", obj.f16, Integer.class);
        checkSecKey(obj, "f17", obj.f17, Long.class);
        checkSecKey(obj, "f18", obj.f18, Float.class);
        checkSecKey(obj, "f19", obj.f19, Double.class);
        checkSecKey(obj, "f20", obj.f20, CompositeKey.class);

        checkSecMultiKey(obj, "f21", toSet(obj.f21), Integer.class);
        checkSecMultiKey(obj, "f22", toSet(obj.f22), Integer.class);
        checkSecMultiKey(obj, "f23", toSet(obj.f23), Integer.class);
        checkSecMultiKey(obj, "f24", toSet(obj.f24), CompositeKey.class);
        checkSecMultiKey(obj, "f25", toSet(obj.f25), CompositeKey.class);

        nullifySecKey(obj, "f8", obj.f8, String.class);
        //nullifySecKey(obj, "f9", obj.f9, BigInteger.class);
        //nullifySecKey(obj, "f10", obj.f10, BigDecimal.class);
        nullifySecKey(obj, "f11", obj.f11, Date.class);
        nullifySecKey(obj, "f12", obj.f12, Boolean.class);
        nullifySecKey(obj, "f13", obj.f13, Character.class);
        nullifySecKey(obj, "f14", obj.f14, Byte.class);
        nullifySecKey(obj, "f15", obj.f15, Short.class);
        nullifySecKey(obj, "f16", obj.f16, Integer.class);
        nullifySecKey(obj, "f17", obj.f17, Long.class);
        nullifySecKey(obj, "f18", obj.f18, Float.class);
        nullifySecKey(obj, "f19", obj.f19, Double.class);
        nullifySecKey(obj, "f20", obj.f20, CompositeKey.class);

        nullifySecMultiKey(obj, "f21", obj.f21, Integer.class);
        nullifySecMultiKey(obj, "f22", obj.f22, Integer.class);
        nullifySecMultiKey(obj, "f23", obj.f23, Integer.class);
        nullifySecMultiKey(obj, "f24", obj.f24, CompositeKey.class);
        nullifySecMultiKey(obj, "f25", obj.f25, CompositeKey.class);

        nullifySecKey(obj, "f31", obj.f31, Date.class);
        nullifySecKey(obj, "f32", obj.f32, Boolean.class);
        nullifySecKey(obj, "f33", obj.f33, Character.class);
        nullifySecKey(obj, "f34", obj.f34, Byte.class);
        nullifySecKey(obj, "f35", obj.f35, Short.class);
        nullifySecKey(obj, "f36", obj.f36, Integer.class);
        nullifySecKey(obj, "f37", obj.f37, Long.class);
        nullifySecKey(obj, "f38", obj.f38, Float.class);
        nullifySecKey(obj, "f39", obj.f39, Double.class);
        nullifySecKey(obj, "f40", obj.f40, CompositeKey.class);

        close();
    }

    static Set toSet(int[] a) {
        Set set = new HashSet();
        for (int i : a) {
            set.add(i);
        }
        return set;
    }

    static Set toSet(Object[] a) {
        return new HashSet(Arrays.asList(a));
    }

    static Set toSet(Set s) {
        return s;
    }

    @Entity
    static class SecKeys implements MyEntity {

        @PrimaryKey
        long id;

        @SecondaryKey(relate=MANY_TO_ONE)
        private boolean f0 = false;
        private boolean g0 = false;

        @SecondaryKey(relate=MANY_TO_ONE)
        private char f1 = '1';
        private char g1 = '1';

        @SecondaryKey(relate=MANY_TO_ONE)
        private byte f2 = 2;
        private byte g2 = 2;

        @SecondaryKey(relate=MANY_TO_ONE)
        private short f3 = 3;
        private short g3 = 3;

        @SecondaryKey(relate=MANY_TO_ONE)
        private int f4 = 4;
        private int g4 = 4;

        @SecondaryKey(relate=MANY_TO_ONE)
        private long f5 = 5;
        private long g5 = 5;

        @SecondaryKey(relate=MANY_TO_ONE)
        private float f6 = 6.6f;
        private float g6 = 6.6f;

        @SecondaryKey(relate=MANY_TO_ONE)
        private double f7 = 7.7;
        private double g7 = 7.7;

        @SecondaryKey(relate=MANY_TO_ONE)
        private String f8 = "8";
        private String g8 = "8";

        //@SecondaryKey(relate=MANY_TO_ONE)
        //private BigInteger f9;
        //private BigInteger g9;

        //@SecondaryKey(relate=MANY_TO_ONE)
        //private BigDecimal f10;
        //private BigDecimal g10;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Date f11 = new Date(11);
        private Date g11 = new Date(11);

        @SecondaryKey(relate=MANY_TO_ONE)
        private Boolean f12 = true;
        private Boolean g12 = true;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Character f13 = '3';
        private Character g13 = '3';

        @SecondaryKey(relate=MANY_TO_ONE)
        private Byte f14 = 14;
        private Byte g14 = 14;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Short f15 = 15;
        private Short g15 = 15;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Integer f16 = 16;
        private Integer g16 = 16;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Long f17= 17L;
        private Long g17= 17L;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Float f18 = 18.18f;
        private Float g18 = 18.18f;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Double f19 = 19.19;
        private Double g19 = 19.19;

        @SecondaryKey(relate=MANY_TO_ONE)
        private CompositeKey f20 = new CompositeKey(20, 20L, "20");
        private CompositeKey g20 = new CompositeKey(20, 20L, "20");

        private static int[] arrayOfInt = { 100, 101, 102 };

        private static Integer[] arrayOfInteger = { 100, 101, 102 };

        private static CompositeKey[] arrayOfCompositeKey = {
            new CompositeKey(100, 100L, "100"),
            new CompositeKey(101, 101L, "101"),
            new CompositeKey(102, 102L, "102"),
        };

        @SecondaryKey(relate=ONE_TO_MANY)
        private int[] f21 = arrayOfInt;
        private int[] g21 = f21;

        @SecondaryKey(relate=ONE_TO_MANY)
        private Integer[] f22 = arrayOfInteger;
        private Integer[] g22 = f22;

        @SecondaryKey(relate=ONE_TO_MANY)
        private Set<Integer> f23 = toSet(arrayOfInteger);
        private Set<Integer> g23 = f23;

        @SecondaryKey(relate=ONE_TO_MANY)
        private CompositeKey[] f24 = arrayOfCompositeKey;
        private CompositeKey[] g24 = f24;

        @SecondaryKey(relate=ONE_TO_MANY)
        private Set<CompositeKey> f25 = toSet(arrayOfCompositeKey);
        private Set<CompositeKey> g25 = f25;

        /* Repeated key values to test shared references. */

        @SecondaryKey(relate=MANY_TO_ONE)
        private Date f31 = f11;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Boolean f32 = f12;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Character f33 = f13;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Byte f34 = f14;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Short f35 = f15;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Integer f36 = f16;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Long f37= f17;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Float f38 = f18;

        @SecondaryKey(relate=MANY_TO_ONE)
        private Double f39 = f19;

        @SecondaryKey(relate=MANY_TO_ONE)
        private CompositeKey f40 = f20;

        public Object getPriKeyObject() {
            return id;
        }

        public void validate(Object other) {
            SecKeys o = (SecKeys) other;
            TestCase.assertEquals(id, o.id);

            TestCase.assertEquals(f0, o.f0);
            TestCase.assertEquals(f1, o.f1);
            TestCase.assertEquals(f2, o.f2);
            TestCase.assertEquals(f3, o.f3);
            TestCase.assertEquals(f4, o.f4);
            TestCase.assertEquals(f5, o.f5);
            TestCase.assertEquals(f6, o.f6);
            TestCase.assertEquals(f7, o.f7);
            TestCase.assertEquals(f8, o.f8);
            //TestCase.assertEquals(f9, o.f9);
            //TestCase.assertEquals(f10, o.f10);
            TestCase.assertEquals(f11, o.f11);
            TestCase.assertEquals(f12, o.f12);
            TestCase.assertEquals(f13, o.f13);
            TestCase.assertEquals(f14, o.f14);
            TestCase.assertEquals(f15, o.f15);
            TestCase.assertEquals(f16, o.f16);
            TestCase.assertEquals(f17, o.f17);
            TestCase.assertEquals(f18, o.f18);
            TestCase.assertEquals(f19, o.f19);
            TestCase.assertEquals(f20, o.f20);
            TestCase.assertTrue(Arrays.equals(f21, o.f21));
            TestCase.assertTrue(Arrays.equals(f22, o.f22));
            TestCase.assertEquals(f23, o.f23);
            TestCase.assertTrue(Arrays.equals(f24, o.f24));
            TestCase.assertEquals(f25, o.f25);

            TestCase.assertEquals(g0, o.g0);
            TestCase.assertEquals(g1, o.g1);
            TestCase.assertEquals(g2, o.g2);
            TestCase.assertEquals(g3, o.g3);
            TestCase.assertEquals(g4, o.g4);
            TestCase.assertEquals(g5, o.g5);
            TestCase.assertEquals(g6, o.g6);
            TestCase.assertEquals(g7, o.g7);
            TestCase.assertEquals(g8, o.g8);
            //TestCase.assertEquals(g9, o.g9);
            //TestCase.assertEquals(g10, o.g10);
            TestCase.assertEquals(g11, o.g11);
            TestCase.assertEquals(g12, o.g12);
            TestCase.assertEquals(g13, o.g13);
            TestCase.assertEquals(g14, o.g14);
            TestCase.assertEquals(g15, o.g15);
            TestCase.assertEquals(g16, o.g16);
            TestCase.assertEquals(g17, o.g17);
            TestCase.assertEquals(g18, o.g18);
            TestCase.assertEquals(g19, o.g19);
            TestCase.assertEquals(g20, o.g20);
            TestCase.assertTrue(Arrays.equals(g21, o.g21));
            TestCase.assertTrue(Arrays.equals(g22, o.g22));
            TestCase.assertEquals(g23, o.g23);
            TestCase.assertTrue(Arrays.equals(g24, o.g24));
            TestCase.assertEquals(g25, o.g25);

            TestCase.assertEquals(f31, o.f31);
            TestCase.assertEquals(f32, o.f32);
            TestCase.assertEquals(f33, o.f33);
            TestCase.assertEquals(f34, o.f34);
            TestCase.assertEquals(f35, o.f35);
            TestCase.assertEquals(f36, o.f36);
            TestCase.assertEquals(f37, o.f37);
            TestCase.assertEquals(f38, o.f38);
            TestCase.assertEquals(f39, o.f39);
            TestCase.assertEquals(f40, o.f40);

            checkSameIfNonNull(o.f31, o.f11);
            checkSameIfNonNull(o.f32, o.f12);
            checkSameIfNonNull(o.f33, o.f13);
            checkSameIfNonNull(o.f34, o.f14);
            checkSameIfNonNull(o.f35, o.f15);
            checkSameIfNonNull(o.f36, o.f16);
            checkSameIfNonNull(o.f37, o.f17);
            checkSameIfNonNull(o.f38, o.f18);
            checkSameIfNonNull(o.f39, o.f19);
            checkSameIfNonNull(o.f40, o.f20);
        }
    }

    public void testSecKeyRefToPriKey()
        throws DatabaseException {

        open();

        SecKeyRefToPriKey obj = new SecKeyRefToPriKey();
        checkEntity(SecKeyRefToPriKey.class, obj);

        checkMetadata(SecKeyRefToPriKey.class.getName(), new String[][] {
                          {"priKey", "java.lang.String"},
                          {"secKey1", "java.lang.String"},
                          {"secKey2", String[].class.getName()},
                          {"secKey3", Set.class.getName()},
                      },
                      0 /*priKeyIndex*/, null);

        checkSecKey(obj, "secKey1", obj.secKey1, String.class);
        checkSecMultiKey(obj, "secKey2", toSet(obj.secKey2), String.class);
        checkSecMultiKey(obj, "secKey3", toSet(obj.secKey3), String.class);

        close();
    }

    @Entity
    static class SecKeyRefToPriKey implements MyEntity {

        @PrimaryKey
        private String priKey;

        @SecondaryKey(relate=ONE_TO_ONE)
        private String secKey1;

        @SecondaryKey(relate=ONE_TO_MANY)
        private String[] secKey2;

        @SecondaryKey(relate=ONE_TO_MANY)
        private Set<String> secKey3 = new HashSet<String>();

        private SecKeyRefToPriKey() {
            priKey = "sharedValue";
            secKey1 = priKey;
            secKey2 = new String[] { priKey };
            secKey3.add(priKey);
        }

        public Object getPriKeyObject() {
            return priKey;
        }

        public void validate(Object other) {
            SecKeyRefToPriKey o = (SecKeyRefToPriKey) other;
            TestCase.assertEquals(priKey, o.priKey);
            TestCase.assertNotNull(o.secKey1);
            TestCase.assertEquals(1, o.secKey2.length);
            TestCase.assertEquals(1, o.secKey3.size());
            TestCase.assertSame(o.secKey1, o.priKey);
            TestCase.assertSame(o.secKey2[0], o.priKey);
            TestCase.assertSame(o.secKey3.iterator().next(), o.priKey);
        }
    }

    private static void checkSameIfNonNull(Object o1, Object o2) {
        if (o1 != null && o2 != null) {
            assertSame(o1, o2);
        }
    }

    private void checkEntity(Class entityCls, MyEntity entity)
        throws DatabaseException {

        Object priKey = entity.getPriKeyObject();
        Class keyCls = priKey.getClass();
        DatabaseEntry keyEntry2 = new DatabaseEntry();
        DatabaseEntry dataEntry2 = new DatabaseEntry();

        /* Write object, read it back and validate (compare) it. */
        PersistEntityBinding entityBinding =
            new PersistEntityBinding(catalog, entityCls.getName(), false);
        entityBinding.objectToData(entity, dataEntry);
        entityBinding.objectToKey(entity, keyEntry);
        Object entity2 = entityBinding.entryToObject(keyEntry, dataEntry);
        entity.validate(entity2);

        /* Read back the primary key and validate it. */
        PersistKeyBinding keyBinding =
            new PersistKeyBinding(catalog, keyCls.getName(), false);
        Object priKey2 = keyBinding.entryToObject(keyEntry);
        assertEquals(priKey, priKey2);
        keyBinding.objectToEntry(priKey2, keyEntry2);
        assertEquals(keyEntry, keyEntry2);

        /* Check raw entity binding. */
        PersistEntityBinding rawEntityBinding =
            new PersistEntityBinding(catalog, entityCls.getName(), true);
        RawObject rawEntity =
            (RawObject) rawEntityBinding.entryToObject(keyEntry, dataEntry);
        rawEntityBinding.objectToKey(rawEntity, keyEntry2);
        rawEntityBinding.objectToData(rawEntity, dataEntry2);
        entity2 = entityBinding.entryToObject(keyEntry2, dataEntry2);
        entity.validate(entity2);
        RawObject rawEntity2 =
            (RawObject) rawEntityBinding.entryToObject(keyEntry2, dataEntry2);
        assertEquals(rawEntity, rawEntity2);
        assertEquals(dataEntry, dataEntry2);
        assertEquals(keyEntry, keyEntry2);

        /* Check raw key binding. */
        PersistKeyBinding rawKeyBinding =
            new PersistKeyBinding(catalog, keyCls.getName(), true);
        Object rawKey = rawKeyBinding.entryToObject(keyEntry);
        rawKeyBinding.objectToEntry(rawKey, keyEntry2);
        priKey2 = keyBinding.entryToObject(keyEntry2);
        assertEquals(priKey, priKey2);
        assertEquals(keyEntry, keyEntry2);
    }
    
    private void checkSecKey(MyEntity entity,
                             String keyName,
                             Object keyValue,
                             Class keyCls)
        throws DatabaseException {
        
        /* Get entity metadata. */
        Class entityCls = entity.getClass();
        EntityMetadata entityMeta =
            model.getEntityMetadata(entityCls.getName());
        assertNotNull(entityMeta);

        /* Get secondary key metadata. */
        SecondaryKeyMetadata secKeyMeta =
            entityMeta.getSecondaryKeys().get(keyName);
        assertNotNull(secKeyMeta);
        
        /* Create key creator/nullifier. */
        SecondaryKeyCreator keyCreator = new PersistKeyCreator
            (catalog, entityMeta, keyCls.getName(), secKeyMeta);

        /* Convert entity to bytes. */
        PersistEntityBinding entityBinding =
            new PersistEntityBinding(catalog, entityCls.getName(), false);
        entityBinding.objectToData(entity, dataEntry);
        entityBinding.objectToKey(entity, keyEntry);

        /* Extract secondary key bytes from entity bytes. */
        DatabaseEntry secKeyEntry = new DatabaseEntry();
        boolean isKeyPresent = keyCreator.createSecondaryKey
            (null, keyEntry, dataEntry, secKeyEntry);
        assertEquals(keyValue != null, isKeyPresent);

        /* Convert secondary key bytes back to an object. */
        PersistKeyBinding keyBinding =
            new PersistKeyBinding(catalog, keyCls.getName(), false);
        if (isKeyPresent) {
            Object keyValue2 = keyBinding.entryToObject(secKeyEntry);
            assertEquals(keyValue, keyValue2);
            DatabaseEntry secKeyEntry2 = new DatabaseEntry();
            keyBinding.objectToEntry(keyValue2, secKeyEntry2);
            assertEquals(secKeyEntry, secKeyEntry2);
        }
    }
    
    private void checkSecMultiKey(MyEntity entity,
                                  String keyName,
                                  Set keyValues,
                                  Class keyCls)
        throws DatabaseException {
        
        /* Get entity metadata. */
        Class entityCls = entity.getClass();
        EntityMetadata entityMeta =
            model.getEntityMetadata(entityCls.getName());
        assertNotNull(entityMeta);

        /* Get secondary key metadata. */
        SecondaryKeyMetadata secKeyMeta =
            entityMeta.getSecondaryKeys().get(keyName);
        assertNotNull(secKeyMeta);
        
        /* Create key creator/nullifier. */
        SecondaryMultiKeyCreator keyCreator = new PersistKeyCreator
            (catalog, entityMeta, keyCls.getName(), secKeyMeta);

        /* Convert entity to bytes. */
        PersistEntityBinding entityBinding =
            new PersistEntityBinding(catalog, entityCls.getName(), false);
        entityBinding.objectToData(entity, dataEntry);
        entityBinding.objectToKey(entity, keyEntry);

        /* Extract secondary key bytes from entity bytes. */
        Set<DatabaseEntry> results = new HashSet<DatabaseEntry>();
        keyCreator.createSecondaryKeys
            (null, keyEntry, dataEntry, results);
        assertEquals(keyValues.size(), results.size());

        /* Convert secondary key bytes back to objects. */
        PersistKeyBinding keyBinding =
            new PersistKeyBinding(catalog, keyCls.getName(), false);
        Set keyValues2 = new HashSet();
        for (DatabaseEntry secKeyEntry : results) {
            Object keyValue2 = keyBinding.entryToObject(secKeyEntry);
            keyValues2.add(keyValue2);
        }
        assertEquals(keyValues, keyValues2);
    }
    
    private void nullifySecKey(MyEntity entity,
                              String keyName,
                              Object keyValue,
                              Class keyCls)
        throws DatabaseException {
        
        /* Get entity metadata. */
        Class entityCls = entity.getClass();
        EntityMetadata entityMeta =
            model.getEntityMetadata(entityCls.getName());
        assertNotNull(entityMeta);

        /* Get secondary key metadata. */
        SecondaryKeyMetadata secKeyMeta =
            entityMeta.getSecondaryKeys().get(keyName);
        assertNotNull(secKeyMeta);
        
        /* Create key creator/nullifier. */
        ForeignMultiKeyNullifier keyNullifier = new PersistKeyCreator
            (catalog, entityMeta, keyCls.getName(), secKeyMeta);

        /* Convert entity to bytes. */
        PersistEntityBinding entityBinding =
            new PersistEntityBinding(catalog, entityCls.getName(), false);
        entityBinding.objectToData(entity, dataEntry);
        entityBinding.objectToKey(entity, keyEntry);

        /* Convert secondary key to bytes. */
        PersistKeyBinding keyBinding =
            new PersistKeyBinding(catalog, keyCls.getName(), false);
        DatabaseEntry secKeyEntry = new DatabaseEntry();
        if (keyValue != null) {
            keyBinding.objectToEntry(keyValue, secKeyEntry);
        }

        /* Nullify secondary key bytes within entity bytes. */
        boolean isKeyPresent = keyNullifier.nullifyForeignKey
            (null, keyEntry, dataEntry, secKeyEntry);
        assertEquals(keyValue != null, isKeyPresent);

        /* Convert modified entity bytes back to an entity. */
        Object entity2 = entityBinding.entryToObject(keyEntry, dataEntry);
        setFieldToNull(entity, keyName);
        entity.validate(entity2);

        /* Do a full check after nullifying it. */
        checkSecKey(entity, keyName, null, keyCls);
    }
    
    private void nullifySecMultiKey(MyEntity entity,
                                    String keyName,
                                    Object keyValue,
                                    Class keyCls)
        throws DatabaseException {
        
        /* Get entity metadata. */
        Class entityCls = entity.getClass();
        EntityMetadata entityMeta =
            model.getEntityMetadata(entityCls.getName());
        assertNotNull(entityMeta);

        /* Get secondary key metadata. */
        SecondaryKeyMetadata secKeyMeta =
            entityMeta.getSecondaryKeys().get(keyName);
        assertNotNull(secKeyMeta);
        
        /* Create key creator/nullifier. */
        ForeignMultiKeyNullifier keyNullifier = new PersistKeyCreator
            (catalog, entityMeta, keyCls.getName(), secKeyMeta);

        /* Convert entity to bytes. */
        PersistEntityBinding entityBinding =
            new PersistEntityBinding(catalog, entityCls.getName(), false);
        entityBinding.objectToData(entity, dataEntry);
        entityBinding.objectToKey(entity, keyEntry);

        /* Get secondary key binding. */
        PersistKeyBinding keyBinding =
            new PersistKeyBinding(catalog, keyCls.getName(), false);
        DatabaseEntry secKeyEntry = new DatabaseEntry();

        /* Nullify one key value at a time until all of them are gone. */
        while (true) {
            Object fieldObj = getField(entity, keyName);
            fieldObj = nullifyFirstElement(fieldObj, keyBinding, secKeyEntry);
            if (fieldObj == null) {
                break;
            }
            setField(entity, keyName, fieldObj);

            /* Nullify secondary key bytes within entity bytes. */
            boolean isKeyPresent = keyNullifier.nullifyForeignKey
                (null, keyEntry, dataEntry, secKeyEntry);
            assertEquals(keyValue != null, isKeyPresent);

            /* Convert modified entity bytes back to an entity. */
            Object entity2 = entityBinding.entryToObject(keyEntry, dataEntry);
            entity.validate(entity2);

            /* Do a full check after nullifying it. */
            Set keyValues;
            if (fieldObj instanceof Set) {
                keyValues = (Set) fieldObj;
            } else if (fieldObj instanceof Object[]) {
                keyValues = toSet((Object[]) fieldObj);
            } else if (fieldObj instanceof int[]) {
                keyValues = toSet((int[]) fieldObj);
            } else {
                throw new IllegalStateException(fieldObj.getClass().getName());
            }
            checkSecMultiKey(entity, keyName, keyValues, keyCls);
        }
    }

    /**
     * Nullifies the first element of an array or collection object by removing
     * it from the array or collection.  Returns the resulting array or
     * collection.  Also outputs the removed element to the keyEntry using the
     * keyBinding.
     */
    private Object nullifyFirstElement(Object obj,
                                       EntryBinding keyBinding,
                                       DatabaseEntry keyEntry) {
        if (obj instanceof Collection) {
            Iterator i = ((Collection) obj).iterator();
            if (i.hasNext()) {
                Object elem = i.next();
                i.remove();
                keyBinding.objectToEntry(elem, keyEntry);
                return obj;
            } else {
                return null;
            }
        } else if (obj instanceof Object[]) {
            Object[] a1 = (Object[]) obj;
            if (a1.length > 0) {
                Object[] a2 = (Object[]) Array.newInstance
                    (obj.getClass().getComponentType(), a1.length - 1);
                System.arraycopy(a1, 1, a2, 0, a2.length);
                keyBinding.objectToEntry(a1[0], keyEntry);
                return a2;
            } else {
                return null;
            }
        } else if (obj instanceof int[]) {
            int[] a1 = (int[]) obj;
            if (a1.length > 0) {
                int[] a2 = new int[a1.length - 1];
                System.arraycopy(a1, 1, a2, 0, a2.length);
                keyBinding.objectToEntry(a1[0], keyEntry);
                return a2;
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException(obj.getClass().getName());
        }
    }

    private void checkMetadata(String clsName,
                               String[][] nameTypePairs,
                               int priKeyIndex,
                               String superClsName)
        throws DatabaseException {
        
        /* Check metadata/types against the live model. */
        checkMetadata
            (catalog, model, clsName, nameTypePairs, priKeyIndex,
             superClsName);

        /*
         * Open a catalog that uses the stored model.
         */
        PersistCatalog storedCatalog = new PersistCatalog
            (null, env, "catalog", null, null, null,
             false /*useCurrentModel*/);
        EntityModel storedModel = storedCatalog.getResolvedModel();

        /* Check metadata/types against the stored catalog/model. */
        checkMetadata
            (storedCatalog, storedModel, clsName, nameTypePairs, priKeyIndex,
             superClsName);

        storedCatalog.close();
    }

    private void checkMetadata(PersistCatalog checkCatalog,
                               EntityModel checkModel,
                               String clsName,
                               String[][] nameTypePairs,
                               int priKeyIndex,
                               String superClsName)
        throws DatabaseException {

        ClassMetadata classMeta = checkModel.getClassMetadata(clsName);
        assertNotNull(clsName, classMeta);

        PrimaryKeyMetadata priKeyMeta = classMeta.getPrimaryKey();
        if (priKeyIndex >= 0) {
            assertNotNull(priKeyMeta);
            String fieldName = nameTypePairs[priKeyIndex][0];
            String fieldType = nameTypePairs[priKeyIndex][1];
            assertEquals(priKeyMeta.getName(), fieldName);
            assertEquals(priKeyMeta.getClassName(), fieldType);
            assertEquals(priKeyMeta.getDeclaringClassName(), clsName);
            assertNull(priKeyMeta.getSequenceName());
        } else {
            assertNull(priKeyMeta);
        }

        RawType type = checkCatalog.getFormat(clsName);
        assertNotNull(type);
        assertEquals(clsName, type.getClassName());
        assertEquals(0, type.getVersion());
        assertTrue(!type.isSimple());
        assertTrue(!type.isPrimitive());
        assertTrue(!type.isEnum());
        assertNull(type.getEnumConstants());
        assertTrue(!type.isArray());
        assertEquals(0, type.getDimensions());
        assertNull(type.getComponentType());
        RawType superType = type.getSuperType();
        if (superClsName != null) {
            assertNotNull(superType);
            assertEquals(superClsName, superType.getClassName());
        } else {
            assertNull(superType);
        }

        Map<String,RawField> fields = type.getFields();
        assertNotNull(fields);

        int nFields = nameTypePairs.length;
        assertEquals(nFields, fields.size());

        for (String[] pair : nameTypePairs) {
            String fieldName = pair[0];
            String fieldType = pair[1];
            Class fieldCls;
            try {
                fieldCls = SimpleCatalog.classForName(fieldType);
            } catch (ClassNotFoundException e) {
                fail(e.toString());
                return; /* For compiler */
            }
            RawField field = fields.get(fieldName);
            assertNotNull(field);
            assertEquals(fieldName, field.getName());
            type = field.getType();
            assertNotNull(type);
            int dim = getArrayDimensions(fieldType);
            while (dim > 0) {
                assertEquals(dim, type.getDimensions());
                assertEquals(dim, getArrayDimensions(fieldType));
                assertEquals(true, type.isArray());
                assertEquals(fieldType, type.getClassName());
                assertEquals(0, type.getVersion());
                assertTrue(!type.isSimple());
                assertTrue(!type.isPrimitive());
                assertTrue(!type.isEnum());
                assertNull(type.getEnumConstants());
                fieldType = getArrayComponent(fieldType, dim);
                type = type.getComponentType();
                assertNotNull(fieldType, type);
                dim -= 1;
            }
            assertEquals(fieldType, type.getClassName());
            List<String> enums = getEnumConstants(fieldType);
            assertEquals(isSimpleType(fieldType), type.isSimple());
            assertEquals(isPrimitiveType(fieldType), type.isPrimitive());
            assertNull(type.getComponentType());
            assertTrue(!type.isArray());
            assertEquals(0, type.getDimensions());
            if (enums != null) {
                assertTrue(type.isEnum());
                assertEquals(enums, type.getEnumConstants());
                assertNull(type.getSuperType());
            } else {
                assertTrue(!type.isEnum());
                assertNull(type.getEnumConstants());
            }
        }
    }

    private List<String> getEnumConstants(String clsName) {
        if (isPrimitiveType(clsName)) {
            return null;
        }
        Class cls;
        try {
            cls = Class.forName(clsName);
        } catch (ClassNotFoundException e) {
            fail(e.toString());
            return null; /* Never happens. */
        }
        if (!cls.isEnum()) {
            return null;
        }
        List<String> enums = new ArrayList<String>();
        Object[] vals = cls.getEnumConstants();
        for (Object val : vals) {
            enums.add(val.toString());
        }
        return enums;
    }

    private String getArrayComponent(String clsName, int dim) {
        clsName = clsName.substring(1);
        if (dim > 1) {
            return clsName;
        }
        if (clsName.charAt(0) == 'L' &&
            clsName.charAt(clsName.length() - 1) == ';') {
            return clsName.substring(1, clsName.length() - 1);
        }
        if (clsName.length() != 1) {
            fail();
        }
        switch (clsName.charAt(0)) {
        case 'Z': return "boolean";
        case 'B': return "byte";
        case 'C': return "char";
        case 'D': return "double";
        case 'F': return "float";
        case 'I': return "int";
        case 'J': return "long";
        case 'S': return "short";
        default: fail();
        }
        return null; /* Should never happen. */
    }

    private static int getArrayDimensions(String clsName) {
        int i = 0;
        while (clsName.charAt(i) == '[') {
            i += 1;
        }
        return i;
    }

    private static boolean isSimpleType(String clsName) {
        return isPrimitiveType(clsName) ||
               clsName.equals("java.lang.Boolean") ||
               clsName.equals("java.lang.Character") ||
               clsName.equals("java.lang.Byte") ||
               clsName.equals("java.lang.Short") ||
               clsName.equals("java.lang.Integer") ||
               clsName.equals("java.lang.Long") ||
               clsName.equals("java.lang.Float") ||
               clsName.equals("java.lang.Double") ||
               clsName.equals("java.lang.String") ||
               clsName.equals("java.math.BigInteger") ||
               clsName.equals("java.math.BigDecimal") ||
               clsName.equals("java.util.Date");
    }

    private static boolean isPrimitiveType(String clsName) {
        return clsName.equals("boolean") ||
               clsName.equals("char") ||
               clsName.equals("byte") ||
               clsName.equals("short") ||
               clsName.equals("int") ||
               clsName.equals("long") ||
               clsName.equals("float") ||
               clsName.equals("double");
    }

    interface MyEntity {
        Object getPriKeyObject();
        void validate(Object other);
    }

    private static boolean nullOrEqual(Object o1, Object o2) {
        return (o1 != null) ? o1.equals(o2) : (o2 == null);
    }

    private static String arrayToString(Object[] array) {
        StringBuffer buf = new StringBuffer();
        buf.append('[');
        for (Object o : array) {
            if (o instanceof Object[]) {
                buf.append(arrayToString((Object[]) o));
            } else {
                buf.append(o);
            }
            buf.append(',');
        }
        buf.append(']');
        return buf.toString();
    }

    private void setFieldToNull(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, null);
        } catch (NoSuchFieldException e) {
            fail(e.toString());
        } catch (IllegalAccessException e) {
            fail(e.toString());
        }
    }

    private void setField(Object obj, String fieldName, Object fieldValue) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e.toString());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.toString());
        }
    }

    private Object getField(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e.toString());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.toString());
        }
    }
}
