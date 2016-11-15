<%@ page contentType="text/html;charset=UTF-8"%>
<script id="prodInfoTempl" type="text/x-jsrender">
                        {{if translateType!='2'}}    
                          <div class="nav-tplist-title bd-bottom pb-10  pt-15">
								<ul>
									<li>翻译内容信息</li>
								</ul>
							</div>
                         {{/if}}
                         {{if translateType=='0'}}         
                            <!--文本-->
							<div  class="form-label">
                               <ul>
									<li class="col-md-12">
										<p class="word">内容类型：</p>
										<p class="col-md-8">文本类</p>
									</li>
							   </ul>
                               <ul>
									<li class="col-md-12">
										<p class="word">原文：</p>
										<p class="col-md-8">{{:prod.needTranslateInfo}}</p>
									</li>

								</ul>
								<ul>
									<li class="col-md-12">
										<p class="word">译文：</p>
                                        {{if state=='24'||state=='25'||state=='40'||state=='41'||state=='42'||state=='50'}}  
										  <p>
											<textarea id="translateInfo" name="prod.translateInfo" class="int-text textarea-xxlarge">{{:prod.translateInfo}}</textarea>
										  </p>
                                        {{else}}
                                             <p class="col-md-8">{{:prod.translateInfo}}</p>
                                         {{/if}} 
									</li>
								</ul>
								<ul>
									<li class="col-md-12">
										<p class="word">提交时间：</p>
										<p>{{:~timesToFmatter(prod.updateTime)}}</p>
									</li>
								</ul>
                            </div>
                          {{/if}}  
                          {{if translateType=='1'}}    
                            <!--文件-->
							<div  class="form-label">
                                 <ul>
									<li class="col-md-12">
										<p class="word">内容类型：</p>
										<p class="col-md-8">文档类</p>
									</li>
								</ul>
                                <ul>
									<li class="col-md-12">
									<table class="table" style="width:85%;">
									<thead>
										<tr>
											<th>文档类型</th>
											<th>文件名称</th>
											<th>提交时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
                                       {{for prodFiles}}
										<tr>
                                            {{if #index==0}}
                                               <td rowspan="{{:fileNum}}">原文内容:</td>
                                            {{/if}}
											
											<td>{{:fileName}}</td>
											<td>{{:~timesToFmatter(fileSubmitTime)}}</td>
											<td>
                                                <input type="hidden" name="fileSaveIds" value="{{:fileSaveId}}"/>
                                                <input type="hidden" name="fileNames" value="{{:fileName}}"/>
                                               
											      <a id="upload_{{:fileSaveId}}" class="biu-btn upload "><i class="icon-upload-alt"> </i>上传 </a> 
                                               
											    <a class="biu-btn download"><i class="icon-download-alt"> </i>下载 </a>
											</td>
										</tr>
                                        {{/for}}
										{{for prodFiles}}
                                        {{if fileTranslateId!=null}}
										<tr>
                                            {{if #index==0}}
                                               <td rowspan="{{:fileNum}}">译文内容:</td>
                                            {{/if}}
											
											<td>{{:fileTranslateName}}</td>
											<td>{{:~timesToFmatter(translateFileSubmitTime)}}</td>
											<td>
                                                <input type="hidden" name="fileTranslateIds" value="{{:fileTranslateId}}"/>
                                                <input type="hidden" name="fileTranslateNames" value="{{:fileTranslateName}}"/>
											    {{if state=='24'||state=='25'||state=='40'||state=='41'||state=='42'||state=='50'}}
											      <a id="upload_{{:fileTranslateId}}" class="biu-btn upload"><i class="icon-upload-alt"> </i>上传 </a> 
                                                {{/if}}
											    <a class="biu-btn download"><i class="icon-download-alt"> </i>下载 </a>
											</td>
										</tr>
                                         {{/if}}
                                        {{/for}}
									</tbody>
								   </table>
									</li>
								</ul>  
                            </div>  
                         {{/if}}                            
</script>