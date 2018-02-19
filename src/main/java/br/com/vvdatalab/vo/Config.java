package br.com.vvdatalab.vo;

public class Config {

	private String banco;
	private String database;
	private String outPath;
	private String password;
	private String server;
	private String table;
	private String typeFile;
	private String user;
	
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getOutPath() {
		return outPath;
	}
	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getTypeFile() {
		return typeFile;
	}
	public void setTypeFile(String typeFile) {
		this.typeFile = typeFile;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Config [banco=" + banco + ", database=" + database + ", outPath=" + outPath + ", password=" + password
				+ ", server=" + server + ", table=" + table + ", typeFile=" + typeFile + ", user=" + user + "]";
	}
	
}
