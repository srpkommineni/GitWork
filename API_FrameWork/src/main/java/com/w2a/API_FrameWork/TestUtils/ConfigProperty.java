package com.w2a.API_FrameWork.TestUtils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({
	"file:src/test/resources/propertiesFiles/config.Properties"//Mention the Property file path
})
public interface ConfigProperty extends Config{

	@Key("baseURI")
	String getBaseURI();
	
	@Key("basePath")
	String getBasePath();
	
	@Key("secretKey")
	String getSecretKey();
	
	@Key("testReportPath")
	String getTestReportFilePath();
	
	@Key("testReportName")
	String getTestReportName();
	
}