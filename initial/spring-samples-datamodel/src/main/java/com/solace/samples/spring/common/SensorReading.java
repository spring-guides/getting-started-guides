/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package com.solace.samples.spring.common;

import java.sql.Timestamp;

public class SensorReading {
        private Timestamp timestamp;
        private String sensorID;
        private Double temperature;
        private SensorReading.BaseUnit baseUnit;

        public enum BaseUnit {
                CELSIUS,
                FAHRENHEIT
        }

        public SensorReading() {
                timestamp = new Timestamp(System.currentTimeMillis());
        }

        public SensorReading(String sensorID, double temperature, BaseUnit baseUnit) {
                this();
                this.sensorID = sensorID;
                this.temperature = temperature;
                this.setBaseUnit(baseUnit);
        }

        public Timestamp getTimestamp() {
                return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
                this.timestamp = timestamp;
        }

        public String getSensorID() {
                return sensorID;
        }

        public void setSensorID(String sensorID) {
                this.sensorID = sensorID;
        }

        public Double getTemperature() {
                return temperature;
        }

        public void setTemperature(Double temperature) {
                this.temperature = temperature;
        }

        public SensorReading.BaseUnit getBaseUnit() {
                return baseUnit;
        }

        public void setBaseUnit(SensorReading.BaseUnit baseUnit) {
                this.baseUnit = baseUnit;
        }

        @Override
        public String toString() {
                return "SensorReading [ "+timestamp + " " + sensorID + " " + String.format("%.1f", temperature) + " " + baseUnit + " ]";
        }
}
