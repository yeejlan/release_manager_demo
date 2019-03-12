<%=view.render("header")%>
<div class="container">
    	<!--<div class="hero-unit">-->
        	<legend>Logs</legend>
        	<form class="form-search" action="/log" method="post">
                <fieldset>
                    <div style="padding:20px 0;">
                        Username: <input type="text" name="username" value="<%=username%>" />&nbsp;&nbsp;&nbsp;&nbsp;
                        Date: <input type="text" name="date" id="datepicker" value="<%=date%>" />&nbsp;&nbsp;&nbsp;&nbsp;
                        <a type="submit" class="btn btn-primary" id="log_search">Search</a>
                    </div>
                </fieldset>
            </form>
            <div class="logList">
            	<legend>Result</legend>
                <div class="pagination pagination-right paginationSet">
                    <ul>
                    	<%=pageStr%>
                    </ul>
                </div>
                <div>
                    <table class="table table-bordered table-striped">
                        <thead>
                            <th>ID</th>
                            <th>User ID</th>
                            <th>User Name</th>
                            <th>Action Name</th>
                            <th>Return Message</th>
                            <th>Datetime</th>
                            <th>IP</th>
                        </thead>
                        <tbody>
							<%
							 for(log in logList){
							%>
                            <tr>
                                <td><%=log['id']%></td>
                                <td><%=log['userid']%></td>
                                <td><%=log['username']%></td>
                                <td><%=log['action_name']%></td>
                                <td><a data-toggle="modal" href="#~" class="returnMessage">Return Message</a>
									<div class="hidMessage"><%=log['return_message']%></div>
								</td>
                                <td><%=log['log_date']%></td>
                                <td><%=log['log_ip']%></td>
								
                            </tr>
                           <%}%>
							
                        </tbody>
                    </table>
                </div>
                <div class="pagination pagination-right paginationSet">
                    <ul>
                    	<%=pageStr%>
                    </ul>
                </div>
            </div>
        </div>
		<div id="myModal" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>Message Info</h3>
			</div>
			<div class="modal-body">
				<p></p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Close</a>
			</div>
		<!--</div>-->
	</div>
<%=view.render("footer")%>