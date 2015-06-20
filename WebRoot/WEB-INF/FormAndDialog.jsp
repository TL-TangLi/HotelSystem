<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <sx:head />
  </head>
  
  <body>
  
  		<!-- dialog and form -->
		<!-- ////////////////////////////////////////////////////////////////////////////////////// -->
		
		
		<!-- /////////入住checkInDialog//////// -->
		<div id="checkInDialog" style="display:none" >
			<center>
			<p>可以入住</p>
				<s:form name="checkInForm"  theme="simple" action="infoCheckInAction" namespace="/main" onsubmit="javascript:return checkInAvalidate()">
					<table class= "ui-widget-content">
						<tr>
							<td><s:textfield  id ="checkIn_cif_rId" name="cif.rId" cssStyle="display:none" /></td>
						</tr>
						<tr>
							<td>姓名:</td>
							<td><s:textfield  id ="checkIn_cif_name" name="cif.name" maxlength="10"/></td>
							<td><p class="warn" id="checkIn_warn_name">*必填</p></td>
						</tr>
					
						<tr>
							<td >电话:</td>
							<td ><s:textfield id ="checkIn_cif_phoneNumber" name="cif.phoneNumber" maxlength="30"/></td>
							<td><p class="warn" id="checkIn_warn_phoneNumber"></p></td>
						</tr>
						<tr>
							<td >入住天数:</td>
							<td ><s:textfield name="days" id="checkIn_days" value="1" maxlength="2"/></td>
							<td><p class="warn" id="checkIn_warn_days">*必填</p></td>
						</tr>
						<tr>
							<td >小时房:</td>
							<td >
							<s:select 
								id ="checkIn_cif_isHourRoom"
						       name="cif.isHourRoom"
						       headerKey="-1" headerValue="是否为小时房..........."
						       list="#{false:'否',true:'是'}"
						       value="selectedMonth" 
						       required="true"
								/>
							</td>
							<td><p class="warn" id="checkIn_warn_isHourRoom">*必填</p></td>
						</tr>
						<tr>
							<td >订单号:</td>
							<td ><s:textfield id ="checkIn_cif_orderId" name="cif.orderId" readonly="true" value="无"/></td>
							<td><sx:submit value=".." executeScripts="true" formId="accessOrderByTypeForm" onclick="beforAccessOrder(1)" notifyTopics="/accessOrderResult"></sx:submit></td>
						</tr>
						<tr>
							<td >入住人数:</td>
							<td ><s:textfield id ="checkIn_cif_numberPeople" name="cif.numberPeople" maxlength="1" /></td>
							<td><p class="warn" id="checkIn_warn_numberPeople">*必填</p></td>
						</tr>
						<tr>
							<td >入住说明:</td>
							<td ><s:textfield  id="checkIn_cif_description" name="cif.description" maxlength="30" /></td>
							<td><p class="warn" id="checkIn_warn_description"></p></td>
						</tr>
						<tr>
							<td >首次入账:</td>
							<td ><s:textfield id ="checkIn_cif_balance" name="cif.balance" value="0.0" maxlength="10"/></td>
							<td><p class="warn" id="checkIn_warn_balance" ></p></td>
						</tr>
						<tr>
							<td >入账类型:</td>
							<td>
								<s:select 
								id ="checkIn_balanceType"
						       name="balanceType"
						       headerKey="-1" 
						       headerValue="选择入账类型..........."
						       list="#{'0':'银行卡', '1':'现金','2':'网络费用','3':'积分兑换'}"
						       required="true"
								/>	
							</td>
							<td><p class="warn" id="checkIn_warn_balanceType" >*必填</p></td>
						</tr>
						<tr>
							<td><sx:submit type="submit"  notifyTopics="/checkInResult" value="入住"/></td>
							<td align="center"><sx:submit type="submit" formId="changerRoomStateForm" onclick="beforChangeRoomState(1)" notifyTopics="/requestChangeRoomStateFromCleanToDirtyResult" value="改为脏房"/></td>
							<td align="right"><button type="button"  onclick="dialogClose('checkInDialog')">取消</button></td>
						</tr>
					</table>
				</s:form>
			</center>
		</div>
		
		
		
		
		
		
		
		
		
		<!-- ////////入住信息updateCheckInInfoDialog//////// -->
		<div  id="updateCheckInInfoDialog" style="display:none">
			<center>
				<p>入住信息修改</p>
				<s:form  theme="simple"  action="updateCheckInAction" namespace="/main" onSubmit="return updateCheckInInfoAvalidate()">
					<table class= "ui-widget-content" >
						<tr>
							<td >状态:</td>
							<td><s:label id="state2" /></td>
							<td><s:textfield cssStyle="display:none" id="updateCheckInInfo_cif_rId" name="cif.rId" /></td>
						</tr>
						<tr id="updateCheckInInfo_orderId_tr">
							<td >订单号:</td>
							<td ><s:textfield id ="orderId2" name="cif.orderId" readonly="true" /></td>
							<td><sx:submit value=".." executeScripts="true" formId="accessOrderByTypeForm" onclick="beforAccessOrder(2)" notifyTopics="/accessOrderResult"></sx:submit></td>
						</tr>
						<tr style="display:none">
							<td ><s:textfield id ="orderId2_beforModify" name="modifyOrder"  /></td>
						</tr>
						<tr style="display:none" id="updateCheckInInfo_isHourRoom_tr">
							<td >关于小时房:</td>
							<td >
							<s:select 
								id ="updateCheckInInfo_cif_isHourRoom"
						       name="cif.isHourRoom"
						       headerKey="-1" headerValue="是否改为正常入住...."
						       list="#{true:'否',false:'是'}"
						       required="true"
								/>
							</td>
							<td><p class="warn" id="updateCheckInInfo_warn_isHourRoom">*必填</p></td>
						<tr>
							<td >联系人:</td>
							<td ><s:textfield id = "name2" name="cif.name"  maxlength="10"/></td>
							<td><p class="warn" id="updateCheckInInfo_warn_name"></p></td>
						</tr>
						<tr>
							<td >入住人数:</td>
							<td ><s:textfield id = "numberPeople2" name="cif.numberPeople" maxlength="1" /></td>
							<td><p class="warn" id="updateCheckInInfo_warn_numberPeople"></p></td>
						</tr>
						<tr>
							<td >电话:</td>
							<td ><s:textfield id="phoneNumber2" name="cif.phoneNumber"  maxlength="30"/></td>
							<td><p class="warn" id="updateCheckInInfo_warn_phoneNumber"></p></td>
						</tr>
						<tr>
							<td >入住说明:</td>
							<td ><s:textfield id="description2" name="cif.description"  maxlength="30" /></td>
							<td><p class="warn" id="updateCheckInInfo_warn_description"></p></td>
						</tr>
						<tr>
							<td >入住时间:</td>
							<td ><s:label id="enterTime2" /></td>
						</tr>
						<tr>
							<td >入账总额:</td>
							<td ><s:label id="balance2" /></td>
							<td><s:label id="enterCount2"/></td>
						</tr>
						<tr>
							<td >余额:</td>
							<td ><s:label id="leftMoney2" /></td>
						</tr>
						</table>
						<table class= "ui-widget-content">
						<tr>
							<td><sx:submit value="修改" notifyTopics="/updateCheckInInfoResult" /></td>
							<!-- <td ><button type="button" id="addBalanceButton" onclick="openAddBalance()">账单</button></td> -->
							<td><a id="balanceButton"  onclick="balanceButtonClick()">账单</a></td>
							<td ><button type="button" id="continueCheckInButton"  onclick="openContinueCheckIn()">续住</button></td>
							<%-- <td><sx:submit value="换房" formId="switchRoomForm" notifyTopics="/checkOutResult" /></td> --%>
							<td><button onclick="beforeRequestEmptyRoom(3)">换房</button></td>
							<td ><button type="button" onclick="dialogClose('updateCheckInInfoDialog')">取消</button></td>
						</tr>
					</table>
				</s:form>
			</center>
		</div>
		
		
		<!-- ////////当日查询空房间为了换房对话框//////// -->
		<div id="queryEmptyRoomForSwitchDialog" class="todayDialog" style="display:none">
		</div>
		
		
		<!-- ///////换房Form//////// -->
		<div id= "switchRoomDialog" style="display: none">
		<s:form id="switchRoomForm" theme="simple" action="switchRoomRoomAction" namespace="/main" onsubmit="javascript:return switchRoomAvalidate()">
			<s:textfield name="targetId" cssStyle="display:none" id="switchRoom_targetId"></s:textfield>
			<s:textfield name="roomId"   cssStyle="display:none" id="switchRoom_roomId"></s:textfield>
			<p class="warn" id="switchRoom_description_id"></p>
			<table>
				<tr>
					<td><sx:submit value="确定" formId="switchRoomForm"  notifyTopics="/switchRoomResult"></sx:submit></td> 
					<td><input type="button" onclick="dialogClose('switchRoomDialog')" value="取消"></td>
				</tr>
			</table>
		</s:form>	
		</div>
		
		
		
		<!-- ////////续住continueCheckInDialog//////// -->
		<div id="continueCheckInDialog" style="display:none">
			<s:form name="continueCheckInForm" theme="simple" action="requestContinueCheckInAction" namespace="/main" onsubmit="javascript:return continueCheckInAvalidate()">
				<table class= "ui-widget-content">
					<tr>
						<td>入住天数:</td>
						<td><s:textfield name="continueDays" id="continueDays" maxlength="2"></s:textfield></td>
						<td><p class="warn" id="continueDays_warn"></p></td>
						<s:textfield name="roomId" cssStyle="display:none" id="continueCheckIn_rId"></s:textfield>
					</tr>
					<tr>
						<td><sx:submit value="确定" notifyTopics="/continueCheckInResult" /></td>
						<td align="right"><button type="button" onclick="dialogClose('continueCheckInDialog')">取消</button></td>
					</tr>
				</table>
			</s:form>
		</div>
		
		
		
		
		<!-- ////////入账addBalanceDialog//////// -->
		<div id="addBalanceDialog" style="display:none">
			<s:form name="addBalanceForm" theme="simple" action="requestAddBalanceActionForBalance" namespace="/main" onsubmit="javascript:return addBalanceAvalidate()">
				<table class= "ui-widget-content">
					<tr>
						<td>类型:</td>
						<td>
							<s:select 
							id ="addBalance_type_id"
							name="type"
					       list="#{'-1':'--请选择--','0':'银行卡', '1':'现金','2':'网络费用','3':'积分兑换'}"
							/>
						</td>
						<td><p class="warn" id="addBalance_type_warn"></p></td>
						<s:textfield name="roomId" cssStyle="display:none" id="addBalance_rId"></s:textfield>
						<s:textfield name="willCheckOut" id ="willCheckOut_Id" cssStyle="display:none"></s:textfield>
					</tr>
					<tr>
						<td>入账金额:</td>
						<td><s:textfield name="addBalance" id="addbalance_balance_id" maxlength="10"></s:textfield></td>
						<td><p class="warn" id="addBalance_balance_warn"></p></td>
					</tr>
					<tr>
						<td>入账说明:</td>
						<td><s:textfield name="description" id="addBalance_description_id" value="正常入账" maxlength="20"></s:textfield></td>
					</tr>
					<tr>
						<td/>
						<td>
							<sx:submit value="确定" notifyTopics="/addBalanceResult" />
							<button type="button" onclick="dialogClose('addBalanceDialog')">取消</button>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		
		
		
		<!-- ////////消费consumeDialog//////// -->
		<div id="consumeDialog" style="display:none">
			<s:form name="consumeDialog" theme="simple" action="requestConsumeActionForBalance" namespace="/main" onsubmit="javascript:return consumeAvalidate()">
				<table class= "ui-widget-content">
					
					<s:textfield name="roomId" cssStyle="display:none" id="addBalance_rId"></s:textfield>
					<tr>
						<td>消费金额:</td>
						<td><s:textfield name="consumeBalance" id="consumeBalance_id" maxlength="10"></s:textfield></td>
						<td><p class="warn" id="consumeBalance_warn"></p></td>
					</tr>
					<tr>
						<td>消费说明:</td>
						<td><s:textfield name="description" id="consumeDescription_id"  maxlength="20"></s:textfield></td>
						<td><p class="warn" id="consumeDescription_warn" ></p></td>
					</tr>
					<tr>
						<td/>
						<td>
							<sx:submit value="确定" notifyTopics="/consumeResult" />
							<button type="button" onclick="dialogClose('consumeDialog')">取消</button>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		
		
		
		
		
		
		<!-- ////////改变房间状态changeRoomDialog//////// -->
		<div id="changeRoomDialog" style="display:none">
			<center>
				<s:form id="changerRoomStateForm" theme="simple" action="changeStateRoomAction" namespace="/main" onsubmit="return changeRoomStateAvaliable()"  >
					<table class= "ui-widget-content" align="center">
						<tr>
							<td>状态：</td>
							<td>
								<s:select 
								id ="changeRoomState_select"
								name="changeState"
						       list="#{1:'干净房',2:'脏房',6:'不可用'}"
								/>
							</td>
							<td><s:textfield name="roomId" cssStyle="display:none" id="changeRoomState_rId"></s:textfield></td>
							<td><sx:submit notifyTopics="/requestChangeRoomStateResult" onclick="beforChangeRoomState(2)" value="修改"/></td>
						</tr>			
					</table>
				</s:form>
				
				<s:form id='changeRoomTypeForm' theme='simple' action='changeRoomTypeRoomAction' namespace='/main' onsubmit='return changeRoomTypeAvaliable()'>
					<table class= 'ui-widget-content' align='center'>
						<tr>
							<td>类型：</td>
							<td id="changeRoomType_list_td"></td>
							<td><s:textfield name='roomId' cssStyle='display:none' id='changeRoomType_rId'></s:textfield></td>
							<td><s:textfield name='oldType' cssStyle='display:none' id='changeRoomType_oldType'></s:textfield></td>
						</tr>
						<tr>
							<td>说明：</td>
							<td><s:textfield name='changeRoomDescription'  maxlength="20"></s:textfield></td>
						</tr>
						<tr>
							<td></td><td>
							<sx:submit notifyTopics='/requestChangeRoomTypeResult' formId="changeRoomTypeForm" value='修改'/>
							<button type="button" onclick="dialogClose('changeRoomDialog')">取消</button></td>
						</tr>			
					</table>
				</s:form>
				
			</center>
		</div>
		
		
		
		

		


		
		
		<!-- ////////添加订单addOrderDialog//////// -->
		<div id="addOrderDialog" style="display:none">
			<center>
				<p class="description">房间数指定该订单产生的个数，订单延迟只是记录推迟该订单过期的小时数。</p>
				<s:form id="addOrderForm" name="OrderForm" theme="simple"  namespace="/main" action="addOrderAction" onsubmit="return addOrderAvalidate()">
					<table class= "ui-widget-content" align="center">
						<tr>
							<td>房型:</td>
							<td id="addOrder_od_roomType_td"></td>
							<td><p id="addOrder_warn_roomType" class="warn">*必填</p> </td>
						</tr>
						<tr>
							<td>入住日期:</td>
							<td><s:textfield name="od.enterDate" id="addOrder_od_enterDate" readonly="true"></s:textfield></td>
							<td><p id="addOrder_warn_enterDate" class="warn">*必填</p> </td>
						</tr>
						<tr>
							<td>预定天数:</td>
							<td><s:textfield name="od.days" id="addOrder_od_days" value="1" maxlength="1"></s:textfield></td>
							<td><p id="addOrder_warn_days" class="warn">*必填</p> </td>
						</tr>
						<tr>
							<td>房间数:</td>
							<td><s:textfield name="od.rooms" id="addOrder_od_rooms" value="1" maxlength="1"></s:textfield></td>
							<td><p id="addOrder_warn_rooms" class="warn">*必填</p> </td>
						</tr>
						<tr>
							<td>姓名:</td>
							<td><s:textfield name="od.name" id="addOrder_od_name" maxlength="10"></s:textfield></td>
							<td><p id="addOrder_warn_name" class="warn">*必填</p> </td>
						</tr>
						<tr>
							<td>电话号码:</td>
							<td><s:textfield name="od.phoneNumber" id="addOrder_od_phoneNumber" maxlength="30"></s:textfield></td>
						</tr>
						<tr>
							<td>说明:</td>
							<td><s:textfield name="od.description" id="addOrder_od_description" maxlength="30"></s:textfield></td>
						</tr>
						<tr>
							<td>订单延迟:</td>
							<td><s:textfield name="od.extendHour" id="addOrder_od_extendHour" value="0" maxlength="1"></s:textfield></td>
						</tr>			
						<tr  ><td/><td align="center"><sx:submit notifyTopics="/addOrderResult"  onclick="beforeAddOrder()" value="确认"/></td></tr>
					</table>
				</s:form>
			</center>
		</div>

		
		
	
		<!-- ////////单个订单对话框accessOneOrderDialog//////// -->
		<div id="accessOneOrderDialog" style="display:none">
			  <s:form theme="simple"  id="updateOrderForm" namespace="/main" action="addOrderAction" onsubmit="return updateOrderAvalidate()" >
				 <table  class="ui-widget ui-widget-content" >
			  		<tr  style="display:none">
				  		<td><s:textfield   name ="od.id" id="updateOrder_id" ></s:textfield></td>
				  		<td><s:textfield   name ="addOrUpdate" value="-1"></s:textfield></td>
			  		</tr>
			  		<tr>
			  			<td>下单时间：</td>
			  			<td><s:label id="updateOrder_orderTime"></s:label></td>
			  		</tr>
			  		<tr>
			  			<td>联系人：</td>
			  			<td><s:textfield name ="od.name"  id="updateOrder_name" maxlength="8"></s:textfield></td>
			  			<td><p id="updateOrder_warn_name" class="warn"></p> </td>
			  		</tr>
			  		<tr>
			  			<td>联系电话：</td>
			  			<td><s:textfield name ="od.phoneNumber"  id="updateOrder_phoneNumber" maxlength="30"></s:textfield></td>
			  			<td><p id="updateOrder_warn_phoneNumber" class="warn"></p> </td>
			  		</tr>
			  		<tr>
			  			<td>说明：</td>
			  			<td><s:textfield name ="od.description" id="updateOrder_description" maxlength="30"></s:textfield></td>
			  			<td><p id="updateOrder_warn_description" class="warn"></p> </td>
			  		</tr>
			  		<tr >
			  			<td>房间类型：</td>
			  			<td id = "updateOrderRoomType_td" ></td>
			  			<td><p id="updateOrder_warn_roomType" class="warn"></p> </td>
			  		</tr>
			  		<tr id="updateOrder_info_tr">
			  			<td>订单状态：</td>
			  			<td >
			  				<s:select 
			  					id = "updateOrder_info"
						       name="od.info"
						       list="#{'0':'未入住状态', '2':'放弃入住状态 ..........'}"
						       required="true"
							/>
			  			</td>
			  		</tr>
			  		<tr>
			  			<td>入住时间：</td>
			  			<td><s:textfield name ="od.enterDate" id="updateOrder_enterDate" readonly="true" ></s:textfield></td>
			  		</tr>
			  		<tr>
			  			<td>入住天数：</td>
			  			<td><s:textfield name ="od.days" id="updateOrder_days" maxlength="1"></s:textfield></td>
			  			<td><p id="updateOrder_warn_days" class="warn">*必填</p> </td>
			  		</tr>
			  		<tr>
			  			<td>延迟时间：</td>
			  			<td><s:textfield name ="od.extendHour"  id="updateOrder_extendHour" maxlength="1"></s:textfield></td>
			  		</tr>
			  		<tr>
			  		 	<td>
			  				<sx:submit value="修改"  id = "updateOrder_submit"  notifyTopics="/delAndUpdateOrderResult" />
			  		 	</td>
			  			<td>
			  				<button onclick="beforeRequestEmptyRoom(2)" id ="chooseRoomToCheckIn_Button" type="button">选择房间入住</button>
			  			</td>
			  			<td>
			  				<sx:submit value="删除"  formId="delOrderForm"  notifyTopics="/delAndUpdateOrderResult" />
			  			</td>
			  		</tr>
			  		
				 </table>
			  </s:form>
		</div>



		 <!-- ///删除订单delOrderForm -->
	  <s:form id="delOrderForm"  style="display:none"   namespace="/main" action="delOrderAction" onsubmit="return delOrderAvalidate()">
	  		<s:textfield style="display:none" id="delOrder_id" name ="od.id" ></s:textfield>
	  </s:form>	



	 





		<!-- ////////添加房间addRoomDialog//////// -->
		<div id="addRoomDialog" style="display:none">
			<center>
				<s:form id="addRoomForm" theme="simple" action="addRoomAction" namespace="/main" onsubmit="return addRoomAvalidate()">
					<table class= "ui-widget-content" align="center">
						<tr>
							<td>房间号:</td>
							<td><s:textfield name="room.id" id="addRoom_room_id" maxlength="10"></s:textfield></td>
							<td><p id= "addRoom_warn_id" class ="warn">*必填</p></td>
						</tr>
						<tr>
							<td>房型:</td>
							<td id ="addRoom_room_type_td"></td>
							<td><p id= "addRoom_warn_type" class ="warn">*必填</p></td>
						</tr>
						<tr>
							<td>说明:</td>
							<td><s:textfield name="room.description" id="addRoom_room_description" maxlength="20"></s:textfield></td>
						</tr>
						<tr><td></td><td align="center"><sx:submit notifyTopics="/addRoomResult" value="确认"/></td></tr>
					</table>
				</s:form>
			</center>
		</div>
	
	
		<!-- ////////请求房间类型列表requestRoomTypeListForm//////// -->
		<s:form id="requestRoomTypeListForm"  action="queryRoomTypeAction" namespace="/main" cssStyle="display:none" >
			<sx:submit id="requestRoomTypeList_submit" notifyTopics="/requestRoomTypeListResult"></sx:submit>
		</s:form>
	
	
		
		
		
		
	
				
		<!-- ////////添加房间类型addRoomTypeDialog//////// -->
		<div id="addRoomTypeDialog" style="display:none">
			<center>
				<s:form theme="simple" action="addRoomTypeAction" namespace="/main" onsubmit="return addRoomTypeAvalidate()">
					<table class= "ui-widget-content" align="center">
						<tr>
							<td>类型名</td>
							<td><s:textfield name="description"  maxlength="8" id="requestAddRoomType_description" ></s:textfield></td>
							<td><p class="warn" id="requestAddRoomType_description_warn">*必填</p></td>
						</tr>			
						<tr>
							<td>全天价格</td>
							<td><s:textfield name="price" id="requestAddRoomType_price"  maxlength="5"></s:textfield></td>
							<td><p class="warn" id="requestAddRoomType_price_warn">*必填</p></td>
						</tr>			
						<tr>
							<td>小时价格</td>
							<td><s:textfield name="hourPrice" id="requestAddRoomType_hourPrice" maxlength="5"></s:textfield></td>
							<td><p class="warn" id="requestAddRoomType_hourPrice_warn">*必填</p></td>
						</tr>			
						<tr><td></td><td><sx:submit value="确认"  notifyTopics="/addRoomTypeResult"/></td></tr>
					</table>
				</s:form>
			</center>
		</div>
		
			
		
		<!-- ////////修改房间信息对话框updateRoomTypeDialog//////// -->
		<div id="updateRoomTypeDialog" style="display:none">
			<center>
				<s:form theme="simple" action="updateRoomTypeAction" namespace="/main" onsubmit="return updateRoomTypeAvalidate()">
					<table class= "ui-widget-content" align="center">
						
						<tr>
							<td>修改的房型名:</td>
							<td id ="updateRoomType-select"></td>
							<td><p class="warn" id="requestUpdateRoomType_type_warn">*必填</p></td>
						</tr>
						<tr>
							<td>房型名</td>
							<td><s:textfield name="description" id="requestUpdateRoomType_description" maxlength="8"></s:textfield></td>
							<td><p class="warn" id="requestUpdateRoomType_description_warn">*必填</p></td>
						</tr>			
						<tr>
							<td>全天价格</td>
							<td><s:textfield name="price" id="requestUpdateRoomType_price" maxlength="5"></s:textfield></td>
							<td><p class="warn" id="requestUpdateRoomType_price_warn">*必填</p></td>
						</tr>			
						<tr>
							<td>小时价格</td>
							<td><s:textfield name="hourPrice" id="requestUpdateRoomType_hourPrice" maxlength="5"></s:textfield></td>
							<td><p class="warn" id="requestUpdateRoomType_hourPrice_warn">*必填</p></td>
						</tr>			
						<tr><td></td><td><sx:submit value="确认"  notifyTopics="/UpdateRoomTypeResult"/></td></tr>
					</table>
				</s:form>
			</center>
		</div>
	
	
		
		<!-- ////////删除房间类型对话框delRoomTypeDialog//////// -->
		<div id="delRoomTypeDialog" style="display:none">
			<center>
				<s:form theme="simple" action="delRoomTypeAction" namespace="/main" onsubmit="return delRoomTypeAvalidate()" >
					<table class= "ui-widget-content" align="center">
						<tr>
							<td>房型:</td>
							<td id ="delRoomType-select"></td>
						</tr>			
						<tr><td></td><td><sx:submit value="确认" notifyTopics="/delRoomTypeResult" /></td></tr>
					</table>
				</s:form>
			</center>
		</div>
	
		
	
		
	
	
			
			
	
	
		<!-- ////////当日查询剩余房间对话框//////// -->
		<div id="requestTodayRoomStatisticsDialog" class="todayDialog" style="display:none">
		</div>
		
				
		<!-- //////////请求房间统计Form//////// -->
		<s:form id="requestTodayRoomStatisticsForm" cssStyle="display:none" theme="simple" action="requestTodayRoomStatisticsAction" namespace="/main" onSubmit="return requestTodayRoomStatisticsAvalidate()" >
			<td><sx:submit id="requestTodayRoomStatistics_submit" executeScripts="true"   notifyTopics="/requestTodayRoomStatisticsResult"></sx:submit></td>
		</s:form>
		
		
		
		
		
	
	
		<!-- ////////当日查询空房间为了入住对话框//////// -->
		<div id="queryEmptyRoomForCheckInDialog" class="todayDialog" style="display:none">
		</div>
		
		<!-- ////////当日查询空房间对话框//////// -->
		<div id="queryEmptyRoomDialog" class="todayDialog" style="display:none">
		</div>
		
	
		<!-- //////请求查询空房间Form//////// -->
		<s:form id="requestQueryEmptyRoomForm" cssStyle="display:none" theme="simple" action="todayRoomEmptyAction" namespace="/main" >
			<s:textfield name="type"  		id="queryEmptyRoom_type" ></s:textfield>
			<s:textfield name="state"  	   id="queryEmptyRoom_state" ></s:textfield>
			<sx:submit id="requestQueryEmptyRoom_submit" formId="requestQueryEmptyRoomForm"  executeScripts="true"   notifyTopics="/requestQueryEmptyRoomResult"></sx:submit>
		</s:form>
		
		
		
		<!-- ////////当日查询入住房间对话框//////// -->
		<div id="queryRoomDialog" class="todayDialog" style="display:none">
		</div>
		
	
		<!-- //////请求查询入住房间Form//////// -->
		<s:form id="requestQueryRoomForm" cssStyle="display:none" theme="simple" action="todayRoomEnteredAction" namespace="/main" onSubmit="return requestQueryRoomAvalidate()" >
			<s:textfield name="type"  		id="queryRoom_type" ></s:textfield>
			<s:textfield name="enterDate"  	id="queryRoom_enterDate" ></s:textfield>
			<s:textfield name="outDate"  	id="queryRoom_outDate" ></s:textfield>
			<sx:submit id="requestQueryRoom_submit" executeScripts="true"   notifyTopics="/requestQueryRoomResult"></sx:submit>
		</s:form>
		
		
		
	
	
		<!-- ////////当日订单对话框 (入住)todayOrderDialogCheckIn//////// -->
		<div id="todayOrderDialogCheckIn" class="todayDialog" style="display:none">
		</div>
		
		
		<!-- ////////当日订单对话框 todayOrderDialog//////// -->
		<div id="todayOrderDialog" style="display:none">
		</div>
		
	
		<!-- //////////请求订单Form//////// -->
		<s:form id="accessOrderByTypeForm" cssStyle="display:none" theme="simple" action="todayOrderSumAction" namespace="/main" onSubmit="return accessOrderAvalidate()" >
			<s:textfield name="orientaionName" id="accessOrder_orientaionName" ></s:textfield>
			<s:textfield name="type"  		id="accessOrder_type" ></s:textfield>
			<s:textfield name="info"  		id="accessOrder_info" ></s:textfield>
			<s:textfield name="enterDate"  	id="accessOrder_enterDate" ></s:textfield>
			<s:textfield name="orderDate"  	id="accessOrder_orderDate" ></s:textfield>
			<s:textfield name="comeFrom" 	id="accessOrder_comeFrom"></s:textfield>
			<td><sx:submit id="accessOrder_submit" executeScripts="true"   notifyTopics="/accessOrderResult"></sx:submit></td>
		</s:form>	
	
  
  
  
  </body>
</html>
