# WorkdayIntegrator-HR

How to Use
-------
# Configure workday.properties as below
```properties

workday.hr.target.version=v27.0
workday.hr.target.tenant=yourTenantName
workday.hr.target.host=https://yourworkdayhostname
workday.hr.target.username=IntegrationUser
workday.hr.target.password=IntegrationPassword
```
`workday.hr.target.version` is workday version, currently it is working with v27.0.<br />
`workday.hr.target.tenant` is your company tenant name. you can locate tenant name in this url `https://<Host>/<Tenant>/d/home.htmld?redirect=n`<br />
`workday.hr.target.host` is the host name of your workday tenant. in above url you can locate it.<br />
`workday.hr.target.username` and `workday.hr.target.password` you have to create Username in your workday tenant by accessing task **Create Integration System User**. Assign approprite rights to the user.
![Create Integration System User](/screenshots/Createintuser.JPG "Create Integration System User")

# Build application
build this application so that to generate classes from above configuration in `workday.properties` file.<br />
This will generate code in target folder which us used in our code to implement Workday webservice operations.</br />

# Note:
Currently only get-worker functionality is implemented. I really need contributer to make it big project and it will solve alot of people issues when they want to integrate workday with their local enterprize applications.