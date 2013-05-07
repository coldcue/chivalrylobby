package com.chivalrylobby.web.jpa;

import com.google.appengine.api.datastore.Key;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-05-07T14:27:19.493+0200")
@StaticMetamodel(ServerJDO.class)
public class ServerJDO_ {
	public static volatile SingularAttribute<ServerJDO, Key> key;
	public static volatile SingularAttribute<ServerJDO, Boolean> online;
	public static volatile SingularAttribute<ServerJDO, String> ip;
	public static volatile SingularAttribute<ServerJDO, Integer> port;
	public static volatile SingularAttribute<ServerJDO, String> country;
	public static volatile SingularAttribute<ServerJDO, Byte> slot;
	public static volatile SingularAttribute<ServerJDO, Byte> players;
	public static volatile SingularAttribute<ServerJDO, Boolean> tunngle;
	public static volatile SingularAttribute<ServerJDO, Date> lastupdate;
	public static volatile SingularAttribute<ServerJDO, Date> lastonline;
}
