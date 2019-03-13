<%=view.render("header")%>

	<div class="container">
		<legend>Users list</legend>
        <div class="siteList">
            <table class="table table-striped">
                <thead>
                    <th>Id</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Role</th>
                    <th>&nbsp;&nbsp;&nbsp;Action</th>
                </thead>
                <tbody>
					<%
						for(user in userlist){
					%>
                    <tr>
                        <td><%=user.id%></td>
                        <td><%=user.username%></td>
                        <td>*******</td>
                        <td><%=user.role%></td>
                        <td>
                        	<a href="/admin/edit/?id=<%=user.id%>" class="btn">Edit</a>
                            <a href="#myModal" class="btn btn_delUser">Delete</a>
                            <input type="hidden" value="<%=user.id%>" name="hidden"/>
                        </td>
                    </tr>
                    <%}%>
                </tbody>
            </table>   
        </div>
	</div>
    <div id="myModal" class="modal hide fade">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h3>Message</h3>
        </div>
        <div class="modal-body">
            <p>Are you sure to delete ? </p>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn btn-danger">YES</a>
            <a href="#" class="btn" data-dismiss="modal">NO</a>
        </div>
    </div>
<%=view.render("footer")%>