package com.zk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

/**
 * TODO
 * @author cuiran
 * @version TODO
 */
public class ZooKeeperOperator extends AbstractZooKeeper {
	

	/**
	 * 
	 *<b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
	 *@author cuiran
	 *@createDate 2013-01-16 15:08:38
	 *@param path
	 *@param data
	 *@throws KeeperException
	 *@throws InterruptedException
	 */
	public void create(String path,byte[] data)throws KeeperException, InterruptedException{
		/**
		 * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
		 * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
		 */ 
		this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	/**
	 * 
	 *<b>function:</b>获取节点信息
	 *@author cuiran
	 *@createDate 2013-01-16 15:17:22
	 *@param path
	 *@throws KeeperException
	 *@throws InterruptedException
	 */
	public void getChild(String path) throws KeeperException, InterruptedException{   
		try{
			List<String> list=this.zooKeeper.getChildren(path, false);
			if(list.isEmpty()){
				System.out.println(path+"中没有节点");
			}else{
				System.out.println(path+"中存在节点");
				for(String child:list){
					System.out.println("节点为："+child);
				}
			}
		}catch (KeeperException.NoNodeException e) {
			// TODO: handle exception
			 throw e;   

		}
	}
	
	public byte[] getData(String path) throws KeeperException, InterruptedException {   
        return  this.zooKeeper.getData(path, false,null);   
    }  
	
	public void setAcl(final String path, List<ACL> acl, int version){
		try {
			this.zooKeeper.setACL(path, acl, version);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 取消所有权限
	 * @param zkoperator
	 */
	public static void remove(ZooKeeperOperator zkoperator){
		String SUPERAUTH = "super:superpw";
        String DIGEST = "digest";
        zkoperator.addAuth(DIGEST,SUPERAUTH.getBytes());
        ArrayList<ACL> acls = new ArrayList<ACL>();
        acls.add(new ACL(ZooDefs.Perms.ALL,new Id("world","anyone")));
        zkoperator.setAcl("/zkdd",acls,1);
	}
	
	 public static void main(String[] args) {
		 try {   
	            ZooKeeperOperator zkoperator= new ZooKeeperOperator();   
	            zkoperator.connect("10.111.134.213:2181");
	            //zkoperator.addAuth("digest", "zhangsan:123456".getBytes());
	            //byte[] data = new byte[]{'a','b','c','d'};   
	               
	            //zkoperator.create("/root",null);   
	            //System.out.println(Arrays.toString(zkoperator.getData("/root")));   
//	               
//	            zkoperator.create("/root/child1",data);   
//	            System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));   
//	               
	            //zkoperator.create("/root/child2","china is goood".getBytes());   
//	            System.out.println(new String(zkoperator.getData("/root/child2")));   
	               
	          //  String zktest="ZooKeeper的Java API测试";
	         //   zkoperator.create("/root/child3", zktest.getBytes());
	          //  System.out.println("获取设置的信息："+new String(zkoperator.getData("/root/child3")));
	            
	            //System.out.println("节点孩子信息:");   
	            //zkoperator.getChild("/root");   
	               
	            //zkoperator.close();   
	           
	            //System.out.println(new String(zkoperator.getData("/zkaa"),"gbk"));   
	            
	            
	            
	            remove(zkoperator);
	               
	           

	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   

	}
}
