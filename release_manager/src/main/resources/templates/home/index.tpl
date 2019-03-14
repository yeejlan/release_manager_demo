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
            <?
            if(is_array($this->siteOptions) && count($this->siteOptions)>0){
                foreach($this->siteOptions as $siteId => $siteName){
            ?>
                <option value="<?=$siteId?>" <?if($this->siteInfo['id'] == $siteId){?> selected <?}?>><?= $siteName?></option>
            <?
                }
            }
            ?>
         </select>
		 <? if($this->siteInfo) { ?>
        <div class="btn-group pull-right">
            <a href="#~" class="btn btnTask" data-task="getCurrentBranch" >Checkout the Release Branch</a>
            <a href="#~" class="btn btnTask" data-task="update">Update</a>
            <a href="#~" class="btn btnTask" data-task="generate">Generate</a>
        </div>
		<? } ?>
    </div>
	<? if($this->siteInfo) { ?>
    <div class="control-group">
        Select Release Type：<select id="selectReleaseType">
            <option value="exclude" <?php if($this->releaseType == 'exclude' || empty($this->releaseType)):?>selected<?php endif;?>>
                Exclude List
            </option>
            <option value="include" <?php if($this->releaseType == 'include'):?>selected<?php endif;?>>
                Include List
            </option>
        </select>
    </div>
    <div class="releaseOpt">
        <div class="control-group keywords_div">
            <label>
            <?php if($this->releaseType == 'include'):?>
                Include Key words:
            <?php else:?>
                Filter Key words:
            <?php endif;?>
            </label>
            <textarea rows="6" id="keyWords"><?=$this->keyWords?></textarea> 
         </div>
         <a href="#~" class="btn btn-large btn-primary" id="js_testRelease">Test Release</a>
         <a href="#~" class="btn btn-large btn-danger" id="js_release">Release</a>
    </div>
    <div class="well result">
        <iframe id="js_result" <?=$this->frameLink?>></iframe>
    </div>
	<? } ?>
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