package com.chivalrylobby.web.jpa;

import com.google.appengine.api.datastore.Key;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-05-07T15:25:34.631+0200")
@StaticMetamodel(Server.class)
public class Server_ {
	public static volatile SingularAttribute<Server, Key> key;
	public static volatile SingularAttribute<Server, Boolean> online;
	public static volatile SingularAttribute<Server, String> ip;
	public static volatile SingularAttribute<Server, Integer> port;
	public static volatile SingularAttribute<Server, String> country;
	public static volatile SingularAttribute<Server, Byte> slot;
	public static volatile SingularAttribute<Server, Byte> players;
	public static volatile SingularAttribute<Server, Boolean> tunngle;
	public static volatile SingularAttribute<Server, Date> lastupdate;
	public static volatile SingularAttribute<Server, Date> lastonline;
}
