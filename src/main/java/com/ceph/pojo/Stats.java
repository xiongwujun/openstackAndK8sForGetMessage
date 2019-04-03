/**
  * Copyright 2019 bejson.com 
  */
package com.ceph.pojo;

/**
 * Auto-generated: 2019-04-01 8:45:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Stats {

    public double getPercent_used() {
		return percent_used;
	}
	public void setPercent_used(double percent_used) {
		this.percent_used = percent_used;
	}
	private int wr;
    private int quota_objects;
    private long bytes_used;
    private long max_avail;
    private int rd;
    private long rd_bytes;
    private int objects;
    private double percent_used;
    private int kb_used;
    private long quota_bytes;
    public long getTotal_used_bytes() {
		return total_used_bytes;
	}
	public void setTotal_used_bytes(long total_used_bytes) {
		this.total_used_bytes = total_used_bytes;
	}
	public long getTotal_bytes() {
		return total_bytes;
	}
	public void setTotal_bytes(long total_bytes) {
		this.total_bytes = total_bytes;
	}
	public long getTotal_avail_bytes() {
		return total_avail_bytes;
	}
	public void setTotal_avail_bytes(long total_avail_bytes) {
		this.total_avail_bytes = total_avail_bytes;
	}
	private long raw_bytes_used;
    private long wr_bytes;
    private int dirty;
    private long total_used_bytes;
    private long total_bytes;
    private long total_avail_bytes;
    public int getTotal_objects() {
		return total_objects;
	}
	public void setTotal_objects(int total_objects) {
		this.total_objects = total_objects;
	}
	private int total_objects;
    public void setWr(int wr) {
         this.wr = wr;
     }
     public int getWr() {
         return wr;
     }

    public void setQuota_objects(int quota_objects) {
         this.quota_objects = quota_objects;
     }
     public int getQuota_objects() {
         return quota_objects;
     }

    public long getBytes_used() {
		return bytes_used;
	}
	public void setBytes_used(long bytes_used) {
		this.bytes_used = bytes_used;
	}
	public void setMax_avail(long max_avail) {
         this.max_avail = max_avail;
     }
     public long getMax_avail() {
         return max_avail;
     }

    public void setRd(int rd) {
         this.rd = rd;
     }
     public int getRd() {
         return rd;
     }

    public void setRd_bytes(long rd_bytes) {
         this.rd_bytes = rd_bytes;
     }
     public long getRd_bytes() {
         return rd_bytes;
     }

    public void setObjects(int objects) {
         this.objects = objects;
     }
     public int getObjects() {
         return objects;
     }


    public void setKb_used(int kb_used) {
         this.kb_used = kb_used;
     }
     public int getKb_used() {
         return kb_used;
     }

    public void setQuota_bytes(long quota_bytes) {
         this.quota_bytes = quota_bytes;
     }
     public long getQuota_bytes() {
         return quota_bytes;
     }

    public void setRaw_bytes_used(long raw_bytes_used) {
         this.raw_bytes_used = raw_bytes_used;
     }
     public long getRaw_bytes_used() {
         return raw_bytes_used;
     }

    public void setWr_bytes(long wr_bytes) {
         this.wr_bytes = wr_bytes;
     }
     public long getWr_bytes() {
         return wr_bytes;
     }

    public void setDirty(int dirty) {
         this.dirty = dirty;
     }
     public int getDirty() {
         return dirty;
     }

}