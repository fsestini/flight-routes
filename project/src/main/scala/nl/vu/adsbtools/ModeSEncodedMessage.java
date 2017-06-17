package nl.vu.adsbtools;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ModeSEncodedMessage extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    private static final long serialVersionUID = 2177572154043859536L;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ModeSEncodedMessage\",\"namespace\":\"org.opensky.avro.v2\",\"fields\":[{\"name\":\"sensorType\",\"type\":\"string\"},{\"name\":\"sensorLatitude\",\"type\":[\"double\",\"null\"]},{\"name\":\"sensorLongitude\",\"type\":[\"double\",\"null\"]},{\"name\":\"sensorAltitude\",\"type\":[\"double\",\"null\"]},{\"name\":\"timeAtServer\",\"type\":\"double\"},{\"name\":\"timeAtSensor\",\"type\":[\"double\",\"null\"]},{\"name\":\"timestamp\",\"type\":[\"double\",\"null\"]},{\"name\":\"rawMessage\",\"type\":\"string\"},{\"name\":\"sensorSerialNumber\",\"type\":\"int\"},{\"name\":\"RSSIPacket\",\"type\":[\"double\",\"null\"]},{\"name\":\"RSSIPreamble\",\"type\":[\"double\",\"null\"]},{\"name\":\"SNR\",\"type\":[\"double\",\"null\"]},{\"name\":\"confidence\",\"type\":[\"double\",\"null\"]}]}");
    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
    @Deprecated public java.lang.CharSequence sensorType;
    @Deprecated public java.lang.Double sensorLatitude;
    @Deprecated public java.lang.Double sensorLongitude;
    @Deprecated public java.lang.Double sensorAltitude;
    @Deprecated public double timeAtServer;
    @Deprecated public java.lang.Double timeAtSensor;
    @Deprecated public java.lang.Double timestamp;
    @Deprecated public java.lang.CharSequence rawMessage;
    @Deprecated public int sensorSerialNumber;
    @Deprecated public java.lang.Double RSSIPacket;
    @Deprecated public java.lang.Double RSSIPreamble;
    @Deprecated public java.lang.Double SNR;
    @Deprecated public java.lang.Double confidence;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public ModeSEncodedMessage() {}

    /**
     * All-args constructor.
     */
    public ModeSEncodedMessage(java.lang.CharSequence sensorType,
                               java.lang.Double sensorLatitude,
                               java.lang.Double sensorLongitude,
                               java.lang.Double sensorAltitude,
                               java.lang.Double timeAtServer,
                               java.lang.Double timeAtSensor,
                               java.lang.Double timestamp,
                               java.lang.CharSequence rawMessage,
                               java.lang.Integer sensorSerialNumber,
                               java.lang.Double RSSIPacket,
                               java.lang.Double RSSIPreamble,
                               java.lang.Double SNR,
                               java.lang.Double confidence) {
        this.sensorType = sensorType;
        this.sensorLatitude = sensorLatitude;
        this.sensorLongitude = sensorLongitude;
        this.sensorAltitude = sensorAltitude;
        this.timeAtServer = timeAtServer;
        this.timeAtSensor = timeAtSensor;
        this.timestamp = timestamp;
        this.rawMessage = rawMessage;
        this.sensorSerialNumber = sensorSerialNumber;
        this.RSSIPacket = RSSIPacket;
        this.RSSIPreamble = RSSIPreamble;
        this.SNR = SNR;
        this.confidence = confidence;
    }

    public org.apache.avro.Schema getSchema() { return SCHEMA$; }
    // Used by DatumWriter.  Applications should not call.
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0: return sensorType;
            case 1: return sensorLatitude;
            case 2: return sensorLongitude;
            case 3: return sensorAltitude;
            case 4: return timeAtServer;
            case 5: return timeAtSensor;
            case 6: return timestamp;
            case 7: return rawMessage;
            case 8: return sensorSerialNumber;
            case 9: return RSSIPacket;
            case 10: return RSSIPreamble;
            case 11: return SNR;
            case 12: return confidence;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }
    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0: sensorType = (java.lang.CharSequence)value$; break;
            case 1: sensorLatitude = (java.lang.Double)value$; break;
            case 2: sensorLongitude = (java.lang.Double)value$; break;
            case 3: sensorAltitude = (java.lang.Double)value$; break;
            case 4: timeAtServer = (java.lang.Double)value$; break;
            case 5: timeAtSensor = (java.lang.Double)value$; break;
            case 6: timestamp = (java.lang.Double)value$; break;
            case 7: rawMessage = (java.lang.CharSequence)value$; break;
            case 8: sensorSerialNumber = (java.lang.Integer)value$; break;
            case 9: RSSIPacket = (java.lang.Double)value$; break;
            case 10: RSSIPreamble = (java.lang.Double)value$; break;
            case 11: SNR = (java.lang.Double)value$; break;
            case 12: confidence = (java.lang.Double)value$; break;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    /**
     * Gets the value of the 'sensorType' field.
     */
    public java.lang.CharSequence getSensorType() {
        return sensorType;
    }

    /**
     * Sets the value of the 'sensorType' field.
     * @param value the value to set.
     */
    public void setSensorType(java.lang.CharSequence value) {
        this.sensorType = value;
    }

    /**
     * Gets the value of the 'sensorLatitude' field.
     */
    public java.lang.Double getSensorLatitude() {
        return sensorLatitude;
    }

    /**
     * Sets the value of the 'sensorLatitude' field.
     * @param value the value to set.
     */
    public void setSensorLatitude(java.lang.Double value) {
        this.sensorLatitude = value;
    }

    /**
     * Gets the value of the 'sensorLongitude' field.
     */
    public java.lang.Double getSensorLongitude() {
        return sensorLongitude;
    }

    /**
     * Sets the value of the 'sensorLongitude' field.
     * @param value the value to set.
     */
    public void setSensorLongitude(java.lang.Double value) {
        this.sensorLongitude = value;
    }

    /**
     * Gets the value of the 'sensorAltitude' field.
     */
    public java.lang.Double getSensorAltitude() {
        return sensorAltitude;
    }

    /**
     * Sets the value of the 'sensorAltitude' field.
     * @param value the value to set.
     */
    public void setSensorAltitude(java.lang.Double value) {
        this.sensorAltitude = value;
    }

    /**
     * Gets the value of the 'timeAtServer' field.
     */
    public java.lang.Double getTimeAtServer() {
        return timeAtServer;
    }

    /**
     * Sets the value of the 'timeAtServer' field.
     * @param value the value to set.
     */
    public void setTimeAtServer(java.lang.Double value) {
        this.timeAtServer = value;
    }

    /**
     * Gets the value of the 'timeAtSensor' field.
     */
    public java.lang.Double getTimeAtSensor() {
        return timeAtSensor;
    }

    /**
     * Sets the value of the 'timeAtSensor' field.
     * @param value the value to set.
     */
    public void setTimeAtSensor(java.lang.Double value) {
        this.timeAtSensor = value;
    }

    /**
     * Gets the value of the 'timestamp' field.
     */
    public java.lang.Double getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the 'timestamp' field.
     * @param value the value to set.
     */
    public void setTimestamp(java.lang.Double value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the 'rawMessage' field.
     */
    public java.lang.CharSequence getRawMessage() {
        return rawMessage;
    }

    /**
     * Sets the value of the 'rawMessage' field.
     * @param value the value to set.
     */
    public void setRawMessage(java.lang.CharSequence value) {
        this.rawMessage = value;
    }

    /**
     * Gets the value of the 'sensorSerialNumber' field.
     */
    public java.lang.Integer getSensorSerialNumber() {
        return sensorSerialNumber;
    }

    /**
     * Sets the value of the 'sensorSerialNumber' field.
     * @param value the value to set.
     */
    public void setSensorSerialNumber(java.lang.Integer value) {
        this.sensorSerialNumber = value;
    }

    /**
     * Gets the value of the 'RSSIPacket' field.
     */
    public java.lang.Double getRSSIPacket() {
        return RSSIPacket;
    }

    /**
     * Sets the value of the 'RSSIPacket' field.
     * @param value the value to set.
     */
    public void setRSSIPacket(java.lang.Double value) {
        this.RSSIPacket = value;
    }

    /**
     * Gets the value of the 'RSSIPreamble' field.
     */
    public java.lang.Double getRSSIPreamble() {
        return RSSIPreamble;
    }

    /**
     * Sets the value of the 'RSSIPreamble' field.
     * @param value the value to set.
     */
    public void setRSSIPreamble(java.lang.Double value) {
        this.RSSIPreamble = value;
    }

    /**
     * Gets the value of the 'SNR' field.
     */
    public java.lang.Double getSNR() {
        return SNR;
    }

    /**
     * Sets the value of the 'SNR' field.
     * @param value the value to set.
     */
    public void setSNR(java.lang.Double value) {
        this.SNR = value;
    }

    /**
     * Gets the value of the 'confidence' field.
     */
    public java.lang.Double getConfidence() {
        return confidence;
    }

    /**
     * Sets the value of the 'confidence' field.
     * @param value the value to set.
     */
    public void setConfidence(java.lang.Double value) {
        this.confidence = value;
    }

    /**
     * Creates a new ModeSEncodedMessage RecordBuilder.
     * @return A new ModeSEncodedMessage RecordBuilder
     */
    public static ModeSEncodedMessage.Builder newBuilder() {
        return new ModeSEncodedMessage.Builder();
    }

    /**
     * Creates a new ModeSEncodedMessage RecordBuilder by copying an existing Builder.
     * @param other The existing builder to copy.
     * @return A new ModeSEncodedMessage RecordBuilder
     */
    public static ModeSEncodedMessage.Builder newBuilder(ModeSEncodedMessage.Builder other) {
        return new ModeSEncodedMessage.Builder(other);
    }

    /**
     * Creates a new ModeSEncodedMessage RecordBuilder by copying an existing ModeSEncodedMessage instance.
     * @param other The existing instance to copy.
     * @return A new ModeSEncodedMessage RecordBuilder
     */
    public static ModeSEncodedMessage.Builder newBuilder(ModeSEncodedMessage other) {
        return new ModeSEncodedMessage.Builder(other);
    }

    /**
     * RecordBuilder for ModeSEncodedMessage instances.
     */
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ModeSEncodedMessage>
            implements org.apache.avro.data.RecordBuilder<ModeSEncodedMessage> {

        private java.lang.CharSequence sensorType;
        private java.lang.Double sensorLatitude;
        private java.lang.Double sensorLongitude;
        private java.lang.Double sensorAltitude;
        private double timeAtServer;
        private java.lang.Double timeAtSensor;
        private java.lang.Double timestamp;
        private java.lang.CharSequence rawMessage;
        private int sensorSerialNumber;
        private java.lang.Double RSSIPacket;
        private java.lang.Double RSSIPreamble;
        private java.lang.Double SNR;
        private java.lang.Double confidence;

        /** Creates a new Builder */
        private Builder() {
            super(ModeSEncodedMessage.SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         * @param other The existing Builder to copy.
         */
        private Builder(ModeSEncodedMessage.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.sensorType)) {
                this.sensorType = data().deepCopy(fields()[0].schema(), other.sensorType);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.sensorLatitude)) {
                this.sensorLatitude = data().deepCopy(fields()[1].schema(), other.sensorLatitude);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.sensorLongitude)) {
                this.sensorLongitude = data().deepCopy(fields()[2].schema(), other.sensorLongitude);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.sensorAltitude)) {
                this.sensorAltitude = data().deepCopy(fields()[3].schema(), other.sensorAltitude);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.timeAtServer)) {
                this.timeAtServer = data().deepCopy(fields()[4].schema(), other.timeAtServer);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.timeAtSensor)) {
                this.timeAtSensor = data().deepCopy(fields()[5].schema(), other.timeAtSensor);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.timestamp)) {
                this.timestamp = data().deepCopy(fields()[6].schema(), other.timestamp);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.rawMessage)) {
                this.rawMessage = data().deepCopy(fields()[7].schema(), other.rawMessage);
                fieldSetFlags()[7] = true;
            }
            if (isValidValue(fields()[8], other.sensorSerialNumber)) {
                this.sensorSerialNumber = data().deepCopy(fields()[8].schema(), other.sensorSerialNumber);
                fieldSetFlags()[8] = true;
            }
            if (isValidValue(fields()[9], other.RSSIPacket)) {
                this.RSSIPacket = data().deepCopy(fields()[9].schema(), other.RSSIPacket);
                fieldSetFlags()[9] = true;
            }
            if (isValidValue(fields()[10], other.RSSIPreamble)) {
                this.RSSIPreamble = data().deepCopy(fields()[10].schema(), other.RSSIPreamble);
                fieldSetFlags()[10] = true;
            }
            if (isValidValue(fields()[11], other.SNR)) {
                this.SNR = data().deepCopy(fields()[11].schema(), other.SNR);
                fieldSetFlags()[11] = true;
            }
            if (isValidValue(fields()[12], other.confidence)) {
                this.confidence = data().deepCopy(fields()[12].schema(), other.confidence);
                fieldSetFlags()[12] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing ModeSEncodedMessage instance
         * @param other The existing instance to copy.
         */
        private Builder(ModeSEncodedMessage other) {
            super(ModeSEncodedMessage.SCHEMA$);
            if (isValidValue(fields()[0], other.sensorType)) {
                this.sensorType = data().deepCopy(fields()[0].schema(), other.sensorType);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.sensorLatitude)) {
                this.sensorLatitude = data().deepCopy(fields()[1].schema(), other.sensorLatitude);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.sensorLongitude)) {
                this.sensorLongitude = data().deepCopy(fields()[2].schema(), other.sensorLongitude);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.sensorAltitude)) {
                this.sensorAltitude = data().deepCopy(fields()[3].schema(), other.sensorAltitude);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.timeAtServer)) {
                this.timeAtServer = data().deepCopy(fields()[4].schema(), other.timeAtServer);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.timeAtSensor)) {
                this.timeAtSensor = data().deepCopy(fields()[5].schema(), other.timeAtSensor);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.timestamp)) {
                this.timestamp = data().deepCopy(fields()[6].schema(), other.timestamp);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.rawMessage)) {
                this.rawMessage = data().deepCopy(fields()[7].schema(), other.rawMessage);
                fieldSetFlags()[7] = true;
            }
            if (isValidValue(fields()[8], other.sensorSerialNumber)) {
                this.sensorSerialNumber = data().deepCopy(fields()[8].schema(), other.sensorSerialNumber);
                fieldSetFlags()[8] = true;
            }
            if (isValidValue(fields()[9], other.RSSIPacket)) {
                this.RSSIPacket = data().deepCopy(fields()[9].schema(), other.RSSIPacket);
                fieldSetFlags()[9] = true;
            }
            if (isValidValue(fields()[10], other.RSSIPreamble)) {
                this.RSSIPreamble = data().deepCopy(fields()[10].schema(), other.RSSIPreamble);
                fieldSetFlags()[10] = true;
            }
            if (isValidValue(fields()[11], other.SNR)) {
                this.SNR = data().deepCopy(fields()[11].schema(), other.SNR);
                fieldSetFlags()[11] = true;
            }
            if (isValidValue(fields()[12], other.confidence)) {
                this.confidence = data().deepCopy(fields()[12].schema(), other.confidence);
                fieldSetFlags()[12] = true;
            }
        }

        /**
         * Gets the value of the 'sensorType' field.
         * @return The value.
         */
        public java.lang.CharSequence getSensorType() {
            return sensorType;
        }

        /**
         * Sets the value of the 'sensorType' field.
         * @param value The value of 'sensorType'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setSensorType(java.lang.CharSequence value) {
            validate(fields()[0], value);
            this.sensorType = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'sensorType' field has been set.
         * @return True if the 'sensorType' field has been set, false otherwise.
         */
        public boolean hasSensorType() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'sensorType' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearSensorType() {
            sensorType = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'sensorLatitude' field.
         * @return The value.
         */
        public java.lang.Double getSensorLatitude() {
            return sensorLatitude;
        }

        /**
         * Sets the value of the 'sensorLatitude' field.
         * @param value The value of 'sensorLatitude'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setSensorLatitude(java.lang.Double value) {
            validate(fields()[1], value);
            this.sensorLatitude = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'sensorLatitude' field has been set.
         * @return True if the 'sensorLatitude' field has been set, false otherwise.
         */
        public boolean hasSensorLatitude() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'sensorLatitude' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearSensorLatitude() {
            sensorLatitude = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'sensorLongitude' field.
         * @return The value.
         */
        public java.lang.Double getSensorLongitude() {
            return sensorLongitude;
        }

        /**
         * Sets the value of the 'sensorLongitude' field.
         * @param value The value of 'sensorLongitude'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setSensorLongitude(java.lang.Double value) {
            validate(fields()[2], value);
            this.sensorLongitude = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'sensorLongitude' field has been set.
         * @return True if the 'sensorLongitude' field has been set, false otherwise.
         */
        public boolean hasSensorLongitude() {
            return fieldSetFlags()[2];
        }


        /**
         * Clears the value of the 'sensorLongitude' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearSensorLongitude() {
            sensorLongitude = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        /**
         * Gets the value of the 'sensorAltitude' field.
         * @return The value.
         */
        public java.lang.Double getSensorAltitude() {
            return sensorAltitude;
        }

        /**
         * Sets the value of the 'sensorAltitude' field.
         * @param value The value of 'sensorAltitude'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setSensorAltitude(java.lang.Double value) {
            validate(fields()[3], value);
            this.sensorAltitude = value;
            fieldSetFlags()[3] = true;
            return this;
        }

        /**
         * Checks whether the 'sensorAltitude' field has been set.
         * @return True if the 'sensorAltitude' field has been set, false otherwise.
         */
        public boolean hasSensorAltitude() {
            return fieldSetFlags()[3];
        }


        /**
         * Clears the value of the 'sensorAltitude' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearSensorAltitude() {
            sensorAltitude = null;
            fieldSetFlags()[3] = false;
            return this;
        }

        /**
         * Gets the value of the 'timeAtServer' field.
         * @return The value.
         */
        public java.lang.Double getTimeAtServer() {
            return timeAtServer;
        }

        /**
         * Sets the value of the 'timeAtServer' field.
         * @param value The value of 'timeAtServer'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setTimeAtServer(double value) {
            validate(fields()[4], value);
            this.timeAtServer = value;
            fieldSetFlags()[4] = true;
            return this;
        }

        /**
         * Checks whether the 'timeAtServer' field has been set.
         * @return True if the 'timeAtServer' field has been set, false otherwise.
         */
        public boolean hasTimeAtServer() {
            return fieldSetFlags()[4];
        }


        /**
         * Clears the value of the 'timeAtServer' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearTimeAtServer() {
            fieldSetFlags()[4] = false;
            return this;
        }

        /**
         * Gets the value of the 'timeAtSensor' field.
         * @return The value.
         */
        public java.lang.Double getTimeAtSensor() {
            return timeAtSensor;
        }

        /**
         * Sets the value of the 'timeAtSensor' field.
         * @param value The value of 'timeAtSensor'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setTimeAtSensor(java.lang.Double value) {
            validate(fields()[5], value);
            this.timeAtSensor = value;
            fieldSetFlags()[5] = true;
            return this;
        }

        /**
         * Checks whether the 'timeAtSensor' field has been set.
         * @return True if the 'timeAtSensor' field has been set, false otherwise.
         */
        public boolean hasTimeAtSensor() {
            return fieldSetFlags()[5];
        }


        /**
         * Clears the value of the 'timeAtSensor' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearTimeAtSensor() {
            timeAtSensor = null;
            fieldSetFlags()[5] = false;
            return this;
        }

        /**
         * Gets the value of the 'timestamp' field.
         * @return The value.
         */
        public java.lang.Double getTimestamp() {
            return timestamp;
        }

        /**
         * Sets the value of the 'timestamp' field.
         * @param value The value of 'timestamp'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setTimestamp(java.lang.Double value) {
            validate(fields()[6], value);
            this.timestamp = value;
            fieldSetFlags()[6] = true;
            return this;
        }

        /**
         * Checks whether the 'timestamp' field has been set.
         * @return True if the 'timestamp' field has been set, false otherwise.
         */
        public boolean hasTimestamp() {
            return fieldSetFlags()[6];
        }


        /**
         * Clears the value of the 'timestamp' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearTimestamp() {
            timestamp = null;
            fieldSetFlags()[6] = false;
            return this;
        }

        /**
         * Gets the value of the 'rawMessage' field.
         * @return The value.
         */
        public java.lang.CharSequence getRawMessage() {
            return rawMessage;
        }

        /**
         * Sets the value of the 'rawMessage' field.
         * @param value The value of 'rawMessage'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setRawMessage(java.lang.CharSequence value) {
            validate(fields()[7], value);
            this.rawMessage = value;
            fieldSetFlags()[7] = true;
            return this;
        }

        /**
         * Checks whether the 'rawMessage' field has been set.
         * @return True if the 'rawMessage' field has been set, false otherwise.
         */
        public boolean hasRawMessage() {
            return fieldSetFlags()[7];
        }


        /**
         * Clears the value of the 'rawMessage' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearRawMessage() {
            rawMessage = null;
            fieldSetFlags()[7] = false;
            return this;
        }

        /**
         * Gets the value of the 'sensorSerialNumber' field.
         * @return The value.
         */
        public java.lang.Integer getSensorSerialNumber() {
            return sensorSerialNumber;
        }

        /**
         * Sets the value of the 'sensorSerialNumber' field.
         * @param value The value of 'sensorSerialNumber'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setSensorSerialNumber(int value) {
            validate(fields()[8], value);
            this.sensorSerialNumber = value;
            fieldSetFlags()[8] = true;
            return this;
        }

        /**
         * Checks whether the 'sensorSerialNumber' field has been set.
         * @return True if the 'sensorSerialNumber' field has been set, false otherwise.
         */
        public boolean hasSensorSerialNumber() {
            return fieldSetFlags()[8];
        }


        /**
         * Clears the value of the 'sensorSerialNumber' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearSensorSerialNumber() {
            fieldSetFlags()[8] = false;
            return this;
        }

        /**
         * Gets the value of the 'RSSIPacket' field.
         * @return The value.
         */
        public java.lang.Double getRSSIPacket() {
            return RSSIPacket;
        }

        /**
         * Sets the value of the 'RSSIPacket' field.
         * @param value The value of 'RSSIPacket'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setRSSIPacket(java.lang.Double value) {
            validate(fields()[9], value);
            this.RSSIPacket = value;
            fieldSetFlags()[9] = true;
            return this;
        }

        /**
         * Checks whether the 'RSSIPacket' field has been set.
         * @return True if the 'RSSIPacket' field has been set, false otherwise.
         */
        public boolean hasRSSIPacket() {
            return fieldSetFlags()[9];
        }


        /**
         * Clears the value of the 'RSSIPacket' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearRSSIPacket() {
            RSSIPacket = null;
            fieldSetFlags()[9] = false;
            return this;
        }

        /**
         * Gets the value of the 'RSSIPreamble' field.
         * @return The value.
         */
        public java.lang.Double getRSSIPreamble() {
            return RSSIPreamble;
        }

        /**
         * Sets the value of the 'RSSIPreamble' field.
         * @param value The value of 'RSSIPreamble'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setRSSIPreamble(java.lang.Double value) {
            validate(fields()[10], value);
            this.RSSIPreamble = value;
            fieldSetFlags()[10] = true;
            return this;
        }

        /**
         * Checks whether the 'RSSIPreamble' field has been set.
         * @return True if the 'RSSIPreamble' field has been set, false otherwise.
         */
        public boolean hasRSSIPreamble() {
            return fieldSetFlags()[10];
        }


        /**
         * Clears the value of the 'RSSIPreamble' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearRSSIPreamble() {
            RSSIPreamble = null;
            fieldSetFlags()[10] = false;
            return this;
        }

        /**
         * Gets the value of the 'SNR' field.
         * @return The value.
         */
        public java.lang.Double getSNR() {
            return SNR;
        }

        /**
         * Sets the value of the 'SNR' field.
         * @param value The value of 'SNR'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setSNR(java.lang.Double value) {
            validate(fields()[11], value);
            this.SNR = value;
            fieldSetFlags()[11] = true;
            return this;
        }

        /**
         * Checks whether the 'SNR' field has been set.
         * @return True if the 'SNR' field has been set, false otherwise.
         */
        public boolean hasSNR() {
            return fieldSetFlags()[11];
        }


        /**
         * Clears the value of the 'SNR' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearSNR() {
            SNR = null;
            fieldSetFlags()[11] = false;
            return this;
        }

        /**
         * Gets the value of the 'confidence' field.
         * @return The value.
         */
        public java.lang.Double getConfidence() {
            return confidence;
        }

        /**
         * Sets the value of the 'confidence' field.
         * @param value The value of 'confidence'.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder setConfidence(java.lang.Double value) {
            validate(fields()[12], value);
            this.confidence = value;
            fieldSetFlags()[12] = true;
            return this;
        }

        /**
         * Checks whether the 'confidence' field has been set.
         * @return True if the 'confidence' field has been set, false otherwise.
         */
        public boolean hasConfidence() {
            return fieldSetFlags()[12];
        }


        /**
         * Clears the value of the 'confidence' field.
         * @return This builder.
         */
        public ModeSEncodedMessage.Builder clearConfidence() {
            confidence = null;
            fieldSetFlags()[12] = false;
            return this;
        }

        @Override
        public ModeSEncodedMessage build() {
            try {
                ModeSEncodedMessage record = new ModeSEncodedMessage();
                record.sensorType = fieldSetFlags()[0] ? this.sensorType : (java.lang.CharSequence) defaultValue(fields()[0]);
                record.sensorLatitude = fieldSetFlags()[1] ? this.sensorLatitude : (java.lang.Double) defaultValue(fields()[1]);
                record.sensorLongitude = fieldSetFlags()[2] ? this.sensorLongitude : (java.lang.Double) defaultValue(fields()[2]);
                record.sensorAltitude = fieldSetFlags()[3] ? this.sensorAltitude : (java.lang.Double) defaultValue(fields()[3]);
                record.timeAtServer = fieldSetFlags()[4] ? this.timeAtServer : (java.lang.Double) defaultValue(fields()[4]);
                record.timeAtSensor = fieldSetFlags()[5] ? this.timeAtSensor : (java.lang.Double) defaultValue(fields()[5]);
                record.timestamp = fieldSetFlags()[6] ? this.timestamp : (java.lang.Double) defaultValue(fields()[6]);
                record.rawMessage = fieldSetFlags()[7] ? this.rawMessage : (java.lang.CharSequence) defaultValue(fields()[7]);
                record.sensorSerialNumber = fieldSetFlags()[8] ? this.sensorSerialNumber : (java.lang.Integer) defaultValue(fields()[8]);
                record.RSSIPacket = fieldSetFlags()[9] ? this.RSSIPacket : (java.lang.Double) defaultValue(fields()[9]);
                record.RSSIPreamble = fieldSetFlags()[10] ? this.RSSIPreamble : (java.lang.Double) defaultValue(fields()[10]);
                record.SNR = fieldSetFlags()[11] ? this.SNR : (java.lang.Double) defaultValue(fields()[11]);
                record.confidence = fieldSetFlags()[12] ? this.confidence : (java.lang.Double) defaultValue(fields()[12]);
                return record;
            } catch (Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }

    private static final org.apache.avro.io.DatumWriter
            WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

    @Override public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, org.apache.avro.specific.SpecificData.getEncoder(out));
    }

    private static final org.apache.avro.io.DatumReader
            READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

    @Override public void readExternal(java.io.ObjectInput in)
            throws java.io.IOException {
        READER$.read(this, org.apache.avro.specific.SpecificData.getDecoder(in));
    }

}