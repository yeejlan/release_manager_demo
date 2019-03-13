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
		<form class="form-horizontal formSet" action="/siteconfig/doadd" method="post">
			<legend>Add a site</legend>
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="siteName">Site Name :</label>
					<div class="controls">
						<input type="text" class="input-xlarge" id="siteName" name="sitename" value="<%=siteconfig.sitename%>" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="BaseSVNDirctory">Base Dirctory :</label>
					<div class="controls">
						<input type="text" class="input-xlarge" id="BaseSVNDirctory" rows="6"  name="base_dir" value="<%=siteconfig.base_dir%>" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="get_current_branch_command">Checkout Release Branch :</label>
					<div class="controls">
						<textarea class="input-xlarge span8" id="get_current_branch_command" rows="6" name="get_current_branch_command" ><%=siteconfig.get_current_branch_command%></textarea>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="update_command">Update Command :</label>
					<div class="controls">
						<textarea class="input-xlarge span8" id="update_command" rows="6" name="update_command" ><%=siteconfig.update_command%></textarea>
					</div>
				</div>
                                <div class="control-group">
                                        <label class="control-label" for="generate_command">Generate Command :</label>
                                        <div class="controls">
                                                <textarea class="input-xlarge span8" id="generate_command" rows="6" name="generate_command" ><%=siteconfig.generate_command%></textarea>
                                        </div>
                                </div>

				<div class="control-group">
					<label class="control-label" for="test_release_command">Test Release Command :</label>
					<div class="controls">
						<textarea class="input-xlarge span8" id="test_release_command" rows="6" name="test_release_command" ><%=siteconfig.test_release_command%></textarea>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="release_command">Release Command :</label>
					<div class="controls">
						<textarea class="input-xlarge span8" id="release_command" rows="6" name="release_command" ><%=siteconfig.release_command%></textarea>
					</div>
				</div>
                    <div class="control-group">
                        <label class="control-label" for="cache_dir">Cache Dir :</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge span8" id="cache_dir" name="cache_dir"  value="<%=siteconfig.cache_dir%>" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="cache_exclude_dir">Cache Exclude Dir : </label>
                        <div class="controls">
                            <input type="text" class="input-xlarge span8" id="cache_exclude_dir" name="cache_exclude_dir"  value="<%=siteconfig.cache_exclude_dir%>" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="cache_urls">Cache Urls :</label>
                        <div class="controls">
                            <textarea class="input-xlarge span8" id="cache_urls" rows="6" name="cache_urls"><%=siteconfig.cache_urls%></textarea>
                        </div>
                    </div>
				<div style="text-align:center;">
					<a href="#~" class="btn btn-large btn-primary siteFormBtn">Submit</a>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<%=view.render("footer")%>
