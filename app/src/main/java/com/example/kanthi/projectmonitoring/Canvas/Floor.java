package com.example.kanthi.projectmonitoring.Canvas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Floor {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("imagename")
        @Expose
        private String imagename;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("seq")
        @Expose
        private Integer seq;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("lastModifiedDate")
        @Expose
        private String lastModifiedDate;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("zoneId")
        @Expose
        private Integer zoneId;
        @SerializedName("salesAreaId")
        @Expose
        private Integer salesAreaId;
        @SerializedName("shapes")
        @Expose
        private Object shapes = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImagename() {
            return imagename;
        }

        public void setImagename(String imagename) {
            this.imagename = imagename;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Integer getSeq() {
            return seq;
        }

        public void setSeq(Integer seq) {
            this.seq = seq;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(String lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getZoneId() {
            return zoneId;
        }

        public void setZoneId(Integer zoneId) {
            this.zoneId = zoneId;
        }

        public Integer getSalesAreaId() {
            return salesAreaId;
        }

        public void setSalesAreaId(Integer salesAreaId) {
            this.salesAreaId = salesAreaId;
        }

        public Object getShapes() { return this.shapes; }

        public void setShapes(Object shapes) { this.shapes = shapes; }

}