<%=view.render("header")%>

	<div class="container">
		<legend>Sites list</legend>
        <div class="siteList">
            <table class="table table-striped" id="sortable">
                <thead>
					<th>ID</th>
                    <th>Site Name</th>
                    <th>Base Dir</th>
                    <th>&nbsp;&nbsp;&nbsp;Action</th>
                </thead>
                <tbody>
				<%
					for(site in sites){	
                %>
                    <tr>
						<td><%=site.id%></td>
                        <td><%=site.sitename%></td>
                        <td><%=site.base_dir%></td>
                        <td>
                        	<a href="/siteconfig/edit/?id=<%=site.id%>" class="btn">Edit</a>
                            <a href="#myModal" class="btn btn_del">Delete</a>
                            <input type="hidden" value="<%=site.id%>" name="hidden"/>
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