package com.kingschan.blog.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "base_agent")
public class BaseAgent implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String agentKey;
	private String agentBrowserName;
	private String agentBrowserVersion;
	private String agentBrowserEngine;
	private String agentOs;
	private String agentDevice;

	// Constructors

	/** default constructor */
	public BaseAgent() {
	}

	/** full constructor */
	public BaseAgent(String agentKey, String agentBrowserName,
			String agentBrowserVersion, String agentBrowserEngine,
			String agentOs, String agentDevice) {
		this.agentKey = agentKey;
		this.agentBrowserName = agentBrowserName;
		this.agentBrowserVersion = agentBrowserVersion;
		this.agentBrowserEngine = agentBrowserEngine;
		this.agentOs = agentOs;
		this.agentDevice = agentDevice;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "agent_key", nullable = false, length = 400)
	public String getAgentKey() {
		return this.agentKey;
	}

	public void setAgentKey(String agentKey) {
		this.agentKey = agentKey;
	}

	@Column(name = "agent_browser_name", nullable = false, length = 30)
	public String getAgentBrowserName() {
		return this.agentBrowserName;
	}

	public void setAgentBrowserName(String agentBrowserName) {
		this.agentBrowserName = agentBrowserName;
	}

	@Column(name = "agent_browser_version", nullable = false, length = 30)
	public String getAgentBrowserVersion() {
		return this.agentBrowserVersion;
	}

	public void setAgentBrowserVersion(String agentBrowserVersion) {
		this.agentBrowserVersion = agentBrowserVersion;
	}

	@Column(name = "agent_browser_engine", nullable = false, length = 30)
	public String getAgentBrowserEngine() {
		return this.agentBrowserEngine;
	}

	public void setAgentBrowserEngine(String agentBrowserEngine) {
		this.agentBrowserEngine = agentBrowserEngine;
	}

	@Column(name = "agent_os", nullable = false, length = 50)
	public String getAgentOs() {
		return this.agentOs;
	}

	public void setAgentOs(String agentOs) {
		this.agentOs = agentOs;
	}

	@Column(name = "agent_device", nullable = false, length = 50)
	public String getAgentDevice() {
		return this.agentDevice;
	}

	public void setAgentDevice(String agentDevice) {
		this.agentDevice = agentDevice;
	}

}