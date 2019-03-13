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
        	<form class="form-horizontal" action="/admin/doedit/?id=<%=user.id%>" method="post">
            	<legend>Edit user</legend>
                <fieldset>
                <div class="control-group">
                    <label class="control-label" for="input_username">Username :</label>
                    <div class="controls">
                    	<span class="editusername"><%=user.username%></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="input_password">Password :</label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="input_password"  name="password" />
                    </div>
                </div>
				<div class="control-group">
					<label class="control-label" for="input_password">Confirm Password :</label>
					<div class="controls">
						<input type="password" class="input-xlarge" id="input_password"  name="confirmpassword" />
					</div>
				</div>
                <div class="control-group">
                    <label class="control-label">Admin :</label>
                    <div class="controls">
                    	<label class="radio inline">
                        	<input type="radio" name="role" id="show_generate_button" value="admin" <%if(user.role == 'admin'){%>checked<%}%>/>Yes
                        </label>
                        <label class="radio inline">
                        	<input type="radio" name="role" id="hide_generate_button" value="user" <%if(user.role != 'admin'){%>checked<%}%>/>No
                        </label>
                    </div>
                </div> 
                <div class="control-group">
                    <div class="controls">
						<input type="hidden" name="username" value="<%=user.username%>" />
						<input type="hidden" name="id" value="<%=user.id%>" />
                    	<a href="#~" class="btn btn-large btn-primary btn_user">Submit</a>
                    </div>
                </div> 
                </fieldset>
        	</form>
        </div>
	</div>

<%=view.render("footer")%>