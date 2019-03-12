<%=view.render("header")%>

<!--body-->
<div class="container">

	<div class="hero-unit">
		<form class="form-horizontal"  method="post" action="#">
			<fieldset>
				 <div class="control-group">
                    <label class="control-label" for="old_password">Old Password:</label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="old_password"  name="oldpassword" value="<%=oldPassword%>"/>
                    </div>
                </div> 
                <div class="control-group">
                    <label class="control-label" for="new01_password">New Password:</label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="new01_password"  name="newpassword" value="<%=newPassword%>" />
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="new02_password">Confirm New Password:</label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="new02_password"  name="confirmpassword" value="<%=confirmPassword%>" />
                    </div>
                </div> 
                <div class="control-group">
                    <div class="controls">
                    	<a href="#~" class="btn btn-large btn-primary btn_user">Submit</a>
                        <span class="responseArea" name="responseArea"><%=msg%></span>
                    </div>
                </div> 
			</fieldset>
		</form>
	</div>
</div>

<%=view.render("footer")%>