<%=view.render("header")%>

<form action="#" method="post" id="js_releaseForm">
    <input type="hidden" name="task" id="task" value=""/>
    <input type="hidden" name="releaseType"  value=""/>
    <input type="hidden" name="keyWords"  value=""/>
</form>
<div class="container">
    <div class="control-group">
        Select Project: <select name="siteId" id="selectedSite">
            <option value="0">Select Project Please</option>
            <%
            for(site in sites){
            %>
                <option value="<%=site.id%>" <%if(site.id == siteId){%> selected <%}%>><%=site.sitename%></option>
            <%}%>
         </select>
		 <% if(siteInfo) { %>
        <div class="btn-group pull-right">
            <a href="#~" class="btn btnTask" data-task="getCurrentBranch" >Checkout the Release Branch</a>
            <a href="#~" class="btn btnTask" data-task="update">Update</a>
            <a href="#~" class="btn btnTask" data-task="generate">Generate</a>
        </div>
		<%}%>
    </div>
	<% if(siteInfo) { %>
    <div class="control-group">
        Select Release Type：<select id="selectReleaseType">
            <option value="exclude" <%= if(releaseType == 'exclude' || releaseType == '') "selected" else ""%>>
                Exclude List
            </option>
            <option value="include" <%= if(releaseType == 'include') "selected" else ""%>>
                Include List
            </option>
        </select>
    </div>
    <div class="releaseOpt">
        <div class="control-group keywords_div">
            <label>
            <% if(releaseType == 'include'){%>
                Include Key words:
            <%}else{%>
                Filter Key words:
            <%}%>
            </label>
            <textarea rows="6" id="keyWords"><%=keyWords%></textarea> 
         </div>
         <a href="#~" class="btn btn-large btn-primary" id="js_testRelease">Test Release</a>
         <a href="#~" class="btn btn-large btn-danger" id="js_release">Release</a>
    </div>
    <div class="well result">
        <iframe id="js_result" <%=frameLink%>></iframe>
    </div>
	<%}%>
</div>

<div id="myModal" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Message</h3>
    </div>
    <div class="modal-body">
        <p>Are you sure ? </p>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn btn-danger">YES</a>
        <a href="#" class="btn" data-dismiss="modal">NO</a>
    </div>
</div>
        
<%=view.render("footer")%>