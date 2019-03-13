<%=view.render("header")%>
	<div class="container">
		<%if(errStr != ''){%>
		<div class="alert alert-block fade in">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<h4>Error</h4> 
			<p><%=errStr%></p>
		</div>
		<%}%>
		<div class="hero-unit">
        	<form class="form-horizontal" action="/admin/doadd" method="post">
            	<legend>Add a user</legend>
                <fieldset>
                    <div class="control-group">
                        <label class="control-label" for="input_username">Username :</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="input_username"  name="username" value="<%=post['username']%>" />
                        </div>
                    </div> 
                    <div class="control-group">
                        <label class="control-label" for="input_password">Password :</label>
                        <div class="controls">
                            <input type="password" class="input-xlarge" id="input_password"  name="password" value="<%=post['password']%>"/>
                        </div>
                    </div>
					<div class="control-group">
                        <label class="control-label" for="input_password">Confirm Password :</label>
                        <div class="controls">
                            <input type="password" class="input-xlarge" id="input_password"  name="confirmpassword" value="<%=post['confirmpassword']%>" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">Admin :</label>
                        <div class="controls">
                            <label class="radio inline">
                                <input type="radio" name="role" id="show_generate_button" value="admin" <%if(post['role']=='admin'){%>checked<%}%> />Yes
                            </label>
                            <label class="radio inline">
                                <input type="radio" name="role" id="hide_generate_button" value="user" <%if(post['role']!='admin'){%>checked<%}%> />No
                            </label>
                        </div>
                    </div> 
                    <div class="control-group">
                        <div class="controls">
                            <a href="#~" class="btn btn-large btn-primary btn_user">Submit</a>
                        </div>
                    </div> 
                </fieldset>
        	</form>
        </div>
	</div>

<%=view.render("footer")%>