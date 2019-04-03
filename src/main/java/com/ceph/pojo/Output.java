/**
  * Copyright 2019 bejson.com 
  */
package com.ceph.pojo;
import java.util.List;

/**
 * Auto-generated: 2019-04-01 8:45:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Output {

    private List<Pools> pools;
    private Stats stats;
    public void setPools(List<Pools> pools) {
         this.pools = pools;
     }
     public List<Pools> getPools() {
         return pools;
     }

    public void setStats(Stats stats) {
         this.stats = stats;
     }
     public Stats getStats() {
         return stats;
     }

}