<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand" href="/">Release Manager</a>
			<div class="nav-collapse">
				<ul class="nav">
					<li <%= currController == 'index' ? 'class="active"': '' %>><a href="/">Release</a></li>
					<li <%= currController == 'log' ? 'class="active"': '' %>><a href="/log">Logs</a></li>
                    <!--<li class="divider-vertical"></li>-->
					<%if(session['role'] == 'admin'){%>
					<li class="dropdown <%= currController == 'siteconfig' ? 'class="active"': '' %>">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Config 
                        <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                          <li><a href="/siteconfig">Sites list</a></li>
                          <li class="divider"></li>
                          <li><a href="/siteconfig/add">Add a site</a></li>
                        </ul>
                    </li>
                    <li class="dropdown <%print currController == 'admin' ? 'class="active"': '' %>">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin 
                        <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                          <li><a href="/admin">Users list</a></li>
                          <li class="divider"></li>
                          <li><a href="/admin/add">Add a user</a></li>
                        </ul>
                    </li>
					<%}%>
				</ul>
				<ul class="nav pull-right">
					<li id="fat-menu" class="dropdown">
						<%if(session['username']) {%>
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							Logged in as 
							<em class="nav-username"><%=session['username']%></em> 
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="/login/changepassword">Change Password</a></li>
							<li class="divider"></li>
							<li><a href="/login/exit">Log out</a></li>
						</ul>						
						<%}else{%>
						<a href="/login">Hello, log in please.</a>
						<%}%>
					</li>
                </ul>
			</div>
		</div>
	</div>
</div>
