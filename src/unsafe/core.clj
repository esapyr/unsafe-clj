(ns ^{:author "Ryan Serazin"
      :doc "Just a wrapper lib for sun.misc.Unsafe"}
  unsafe.core)

(defn- make-unsafe []
  "reflection!"
  (let [constructor (.getDeclaredConstructor sun.misc.Unsafe nil)]
    (.setAccessible constructor true)
    (.newInstance constructor nil)))

;todo secure this
(defonce the-unsafe (make-unsafe))

;;; Wrapper Functions
(defn address-size []
  (.addressSize the-unsafe))

(defn ^Object allocate-instance 
  [^java.lang.Class c]
  (.allocateInstance the-unsafe c))

(defn ^long allocate-memory 
  [b]
  (.allocateMemory the-unsafe (long b)))

(defn ^int array-base-offset 
  [^java.lang.Class c]
  (.arrayBaseOffset the-unsafe c))

(defn ^int array-index-scale 
  [^java.lang.Class c]
  (.arrayIndexScale the-unsafe c))

(defn ^boolean CAS-int
  [obj offset expected x]
  (let [offset   (long offset)
        expected (int expected)
        x        (int x)]
    (.compareAndSwapInt the-unsafe obj offset expected x)))

(defn ^boolean CAS-long
  [obj offset expected x]
  (let [offset   (long offset)
        expected (long expected)
        x        (long x)]
    (.compareAndSwapLong the-unsafe obj offset expected x)))

(defn ^boolean CAS-obj
  [obj offset expected x]
  (let [offset   (long offset)]
    (.compareAndSwapObject the-unsafe obj offset expected x)))

(defn copy-memory 
  [from foffset to toffset b]
  (let [foffset (long foffset)
        toffset (long toffset)
        b       (long b)]
  (.copyMemory the-unsafe 
               from foffset to toffset b)))

(defn define-anonymous-class 
  [^java.lang.Class host data cp-patches]
  (.defineAnonymousClass the-unsafe 
                         host (bytes data) (object-array cp-patches)))

(defn define-class 
  [^String the-name b off len & util]
  (let [b   (bytes b)
        off (int off)
        len (int len)
        [loader domain] util]
    (if (and (nil? loader) (nil? domain))
      (.defineClass the-unsafe the-name b off len)
      (.defineClass the-unsafe the-name b off len loader domain))))

(defn ensure-class-init
  [^java.lang.Class c]
  (.ensureClassInitialized the-unsafe c))

(defn free-memory 
  [address]
  (let [address (long address)]
    (.freeMemory the-unsafe address)))

(defn ^long get-address
  [address]
  (let [address (long address)]
    (.getAddress the-unsafe address)))

(defn ^boolean get-boolean
  [obj offset]
  (let [offset (long offset)]
    (.getBoolean the-unsafe obj offset)))

(defn ^boolean get-boolean-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getBooleanVolatile the-unsafe obj offset)))

(defn ^byte get-byte 
  ([address]
   (let [address (long address)]
     (.getByte the-unsafe address)))
   ([obj offset]
    (let [offset (long offset)]
      (.getByte the-unsafe obj offset))))

(defn ^byte get-byte-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getByteVolatile the-unsafe obj offset)))

(defn ^char get-char
  ([address]
   (let [address (long address)]
     (.getChar the-unsafe address)))
  ([obj offset]
   (let [offset (long offset)]
     (.getChar the-unsafe obj offset))))

(defn ^char get-char-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getCharVolatile the-unsafe obj offset)))

(defn ^double get-double 
  ([address]
   (let [address (long address)]
     (.getDouble the-unsafe address)))
  ([obj offset]
   (let [offset (long offset)]
     (.getDouble the-unsafe obj offset))))

(defn ^double get-double-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getDoubleVolatile the-unsafe obj offset)))

(defn ^float get-float 
  ([address]
   (let [address (long address)]
     (.getFloat the-unsafe address)))
  ([obj offset]
   (let [offset (long offset)]
     (.getFloat the-unsafe obj offset))))

(defn ^float get-float-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getFloatVolatile the-unsafe obj offset)))

(defn ^int get-int
  ([address]
   (let [address (long address)]
     (.getInt the-unsafe address)))
  ([obj offset]
   (let [offset (long offset)]
     (.getInt the-unsafe obj offset))))

(defn ^int get-int-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getIntVolatile the-unsafe obj offset)))

(defn ^int get-load-average
  [loadavg nelms]
  (let [loadavg (doubles loadavg)
        nelms   (int nelms)]
    (.getLoadAverage the-unsafe loadavg nelms)))

(defn ^long get-long
  ([address]
   (let [address (long address)]
     (.getLong the-unsafe address)))
  ([obj offset]
   (let [offset (long offset)]
     (.getLong the-unsafe obj offset))))

(defn ^long get-long-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getLongVolatile the-unsafe obj offset)))

(defn get-obj
  ([address]
   (let [address (long address)]
     (.getObject the-unsafe address)))
  ([obj offset]
   (let [offset (long offset)]
     (.getObject the-unsafe obj offset))))

(defn get-obj-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getObjectVolatile the-unsafe obj offset)))

(defn ^short get-short
  ([address]
   (let [address (long address)]
     (.getShort the-unsafe address)))
  ([obj offset]
   (let [offset (long offset)]
     (.getShort the-unsafe obj offset))))

(defn ^short get-short-volatile
  [obj offset]
  (let [offset (long offset)]
    (.getShortVolatile the-unsafe obj offset)))

(defn unsafe-monitor-enter
  [obj]
  (.monitorEnter the-unsafe obj))

(defn unsafe-monitor-exit
  [obj]
  (.monitorExit the-unsafe obj))

(defn ^long object-field-offset
  [^java.lang.reflect.Field f]
  (.objectFieldOffset the-unsafe f))

(defn ^int page-size []
  (.pageSize the-unsafe))

(defn park
  [isAbsolute ptime]
  (let [is-absolute (boolean isAbsolute)
        ptime (long ptime)]
    (.park the-unsafe is-absolute ptime)))

(defn put-address
  [address x]
  (let [address (long address)
        x       (long x)]
    (.putAddress the-unsafe address x)))

(defn put-boolean
  [obj offset x]
  (let [offset (long offset)
        x      (boolean x)]
    (.putBoolean the-unsafe offset x)))

(defn put-boolean-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (boolean x)]
    (.putBooleanVolatile the-unsafe offset x)))

(defn put-byte
  [obj offset x]
  (let [offset (long offset)
        x      (byte x)]
    (.putByte the-unsafe offset x)))

(defn put-byte-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (byte x)]
    (.putByteVolatile the-unsafe offset x)))

(defn put-char
  [obj offset x]
  (let [offset (long offset)
        x      (char x)]
    (.putChar the-unsafe offset x)))

(defn put-char-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (char x)]
    (.putCharVolatile the-unsafe offset x)))

(defn put-double
  [obj offset x]
  (let [offset (long offset)
        x      (double x)]
    (.putDouble the-unsafe offset x)))

(defn put-double-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (double x)]
    (.putDoubleVolatile the-unsafe offset x)))

(defn put-float
  [obj offset x]
  (let [offset (long offset)
        x      (float x)]
    (.putFloat the-unsafe offset x)))

(defn put-float-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (float x)]
    (.putFloatVolatile the-unsafe offset x)))

(defn put-int
  [obj offset x]
  (let [offset (long offset)
        x      (int x)]
    (.putInt the-unsafe offset x)))

(defn put-int-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (int x)]
    (.putIntVolatile the-unsafe offset x)))

(defn put-long
  [obj offset x]
  (let [offset (long offset)
        x      (long x)]
    (.putLong the-unsafe offset x)))

(defn put-long-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (long x)]
    (.putLongVolatile the-unsafe offset x)))

(defn put-obj
  [obj offset x]
  (let [offset (long offset)]
    (.putObject the-unsafe offset x)))

(defn put-obj-volatile
  [obj offset x]
  (let [offset (long offset)]
    (.putObjectVolatile the-unsafe offset x)))

(defn put-ordered-int
  [obj offset x]
  (let [offset (long offset)
        x      (int x)]
    (.putOrderedInt the-unsafe offset x)))

(defn put-ordered-long
  [obj offset x]
  (let [offset (long offset)
        x      (long x)]
    (.putOrderedLong the-unsafe offset x)))

(defn put-ordered-obj
  [obj offset x]
  (let [offset (long offset)]
    (.putOrderedObject the-unsafe offset x)))

(defn put-short
  [obj offset x]
  (let [offset (long offset)
        x      (short x)]
    (.putShort the-unsafe offset x)))

(defn put-short-volatile
  [obj offset x]
  (let [offset (long offset)
        x      (short x)]
    (.putShortVolatile the-unsafe offset x)))

(defn ^long reallocate-memory
  [address b]
  (let [address (long address)
        b       (long b)]
    (.reallocateMemory the-unsafe address b)))

(defn set-memory
  ([address bs value]
  (let [address (long address)
        bs      (long bs)
        value   (byte value)]
    (.setMemory the-unsafe address bs value)))
  ([obj offset bs value]
   (let [offset (long offset)
         bs     (long bs)
         value  (byte value)]
     (.setMemory the-unsafe obj offset bs value))))

(defn ^java.lang.Object static-field-base
  [^java.lang.reflect.Field f]
  (.staticFieldBase the-unsafe f))

(defn ^long static-field-offset
  [^java.lang.reflect.Field f]
  (.staticFieldOffset the-unsafe f))

(defn throw-exception
  [^java.lang.Throwable ee]
  (.throwException throw-exception ee))

(defn ^boolean try-monitor-enter
  [obj]
  (.tryMonitorEnter the-unsafe obj))

(defn unpark
  [thread]
  (.unpark the-unsafe thread))

;;; Util functions
(defn all-nonstatic-fields 
  [^java.lang.Class c]
  (let [is-static? (filter #(not (java.lang.reflect.Modifier/isStatic (.getModifiers %))))
        dec-fields (mapcat #(.getDeclaredFields %))
        not-nil    (take-while #(not (nil? %)))
        xf (comp not-nil dec-fields is-static?)]
     (sequence xf (iterate #(.getSuperclass %) c))))

(defn ^long sizeof
  "returns the shallow (doesn't follow pointers & ignores static fields) size 
  of a javaobject."
  [obj]
  (let [max-size (apply max (map #(object-field-offset %)
                                 (all-nonstatic-fields (.getClass obj))))]
  (long (* 8 (+ 1 (/ max-size 8))))))

(defmulti compare-and-swap (fn [obj offset expected x] 
                             (mapv class [obj offset expected x])))
(defmethod compare-and-swap 
  [java.lang.Object long int int] [obj offset expected x]
  (CAS-int obj offset expected x))
(defmethod compare-and-swap 
  [java.lang.Object long long long] [obj offset expected x]
  (CAS-long obj offset expected x))
(defmethod compare-and-swap 
  [java.lang.Object long java.lang.Object java.lang.Object] [obj offset expected x]
  (CAS-obj obj offset expected x))


; Getters and Setters
;{:type int, :volatile? true, :args [obj offset]}
(defmulti unsafe-get :type)
(defmulti unsafe-put :type)

(defmethod unsafe-get boolean ;not sure if you can dispatch on primitives like this
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-boolean-volatile args)
    (apply get-boolean args)))
(defmethod unsafe-put boolean
  [{args :args volatile :volatile?}] 
  (if volatile
    (apply put-boolean-volatile args)
    (apply put-boolean args)))

(defmethod unsafe-get byte
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-byte-volatile args)
    (apply get-byte args)))
(defmethod unsafe-put byte
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-byte args)
    (apply put-byte-volatile args)))

(defmethod unsafe-get char
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-char-volatile args)
    (apply get-char args)))
(defmethod unsafe-put char
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-char-volatile args)
    (apply put-char args)))

(defmethod unsafe-get double
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-double-volatile args)
    (apply get-double args)))
(defmethod unsafe-put double
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-double-volatile args)
    (apply put-double args)))

(defmethod unsafe-get float 
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-float-volatile args)
    (apply get-float args)))
(defmethod unsafe-put float
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-float-volatile args)
    (apply put-float args)))

(defmethod unsafe-get int
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-int-volatile args)
    (apply get-int args)))
(defmethod unsafe-put int
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-int-volatile args)
    (apply put-int args)))

(defmethod unsafe-get long
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-long-volatile args)
    (apply get-long args)))
(defmethod unsafe-put long
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-long-volatile args)
    (apply put-long args)))

(defmethod unsafe-get java.lang.Object 
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-obj-volatile args)
    (apply get-obj args)))
(defmethod unsafe-put java.lang.Object
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-obj-volatile args)
    (apply put-obj args)))

(defmethod unsafe-get short
  [{args :args volatile :volatile?}]
  (if volatile
    (apply get-short-volatile args)
    (apply get-short args)))
(defmethod unsafe-put short
  [{args :args volatile :volatile?}]
  (if volatile
    (apply put-short-volatile args)
    (apply put-short args)))
