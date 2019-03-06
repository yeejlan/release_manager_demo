<%=view.render("header")%>

<!--body-->
<div class="container">
	<%if(errStr){%>
		<div class="alert alert-block fade in">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<h4>Error</h4> 
			<p><%errStr%></p>
		</div>
	<%}%>
	<div class="hero-unit">
		<form class="form-horizontal"  method="post" action="/login/post">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="js_username">Username</label>
					<div class="controls">
						<input type="text"  name="username" class="input-xlarge" placeholder="Username" id="js_username" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="js_password">Password</label>
					<div class="controls">
						<input type="password" name="password" class="input-xlarge" placeholder="Password" id="js_password" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
					   <button type="submit" class="btn btn-primary" value="Login">Log in</button>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<%=view.render("footer")%>