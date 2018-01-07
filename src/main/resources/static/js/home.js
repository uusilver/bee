/**
 * Created by vali on 1/5/2018.
 */
$(function () {
    var targetPhoneNo;
    var loopTimes;
    $.get("getAppInfoFromDb",function (data) {
        targetPhoneNo = data.split("|")[0];
        var appInfoList = data.split("|")[1].split("@");
        var html = "";
        loopTimes = appInfoList.length-1;
        for(var index=0; index<appInfoList.length-1; index++){
            var appInfoStr = appInfoList[index].split("$");
            html+="<tr>"
            html+="<td><img width='50' height='50' src='"+appInfoStr[0]+"'/></td>"
            var applicationId = "applicationName"+index;
            html+="<td><span id='"+applicationId+"'>"+appInfoStr[1]+"</span></td>" //
            var pkgId = "pkg"+index;
            html+="<td><span id='"+pkgId+"'>"+appInfoStr[2]+"</span></td>" //
            //set use flag
            var useFlagText;
            var userFlagId = "useFlag"+index;
            if("true" == appInfoStr[3]){
                useFlagText = "开启";
                html+="<td><input id='"+userFlagId+"' type='checkbox' checked='checked'/><span>"+useFlagText+"</span></td>" //use flag
            }else {
                useFlagText = "关闭";
                html+="<td><input id='"+userFlagId+"' type='checkbox'/><span>"+useFlagText+"</span></td>" //use flag
            }
            var startHourId = "startHourId"+index;
            html+="<td>"+getSelectedHour(appInfoStr[4], startHourId)+"</td>"
            var startMinuteId = "startMinuteId"+index;
            html+="<td>"+getSelectedMinute(appInfoStr[5],startMinuteId)+"</td>"
            var endHourId = "endHourId"+index;
            html+="<td>"+getSelectedHour(appInfoStr[6], endHourId)+"</td>"
            var endMinuteId = "endMinuteId"+index;
            html+="<td>"+getSelectedMinute(appInfoStr[7], endMinuteId)+"</td>"
            var systemFlagId = "systemFlag"+index;
            html+="<td><span id='"+systemFlagId+"'>"+appInfoStr[8]+"</span></td>" //system flag
            html+="</tr>"
        }//end for
        $("#app_info_table").html(html);
        $('table tr').find('td:eq(2)').hide(); //hide pkg
        $('table tr').find('td:eq(8)').hide(); //hide system flag

        $('.switch').on('click', function() {
            if ($(this).prop('checked')) {
                $(this).next('span').text('开启')
            } else {
                $(this).next('span').text('关闭')
            }
        });
        
        $('#save').on('click',function () {
            var result = targetPhoneNo+"|";
            for(var index=0;index<loopTimes; index++){
                var temp;
                var applicationName = $("#applicationName"+index).text();
                var pkg = $("#pkg"+index).text();
                var userFlag;
                if($('#useFlag'+index).is(':checked')) {
                    userFlag = "true";
                }else{
                    userFlag = "false";
                }
                var startHour =  $("#startHourId"+index).find("option:selected").text();
                var startMinute =  $("#startMinuteId"+index).find("option:selected").text();
                var endHour = $("#endHourId"+index).find("option:selected").text();
                var endMinute = $("#endMinuteId"+index).find("option:selected").text();
                var systemFlag = $("#systemFlag"+index).text();
                temp = applicationName+"$"+pkg+"$"+userFlag+"$"+startHour+"$"+startMinute+"$"+endHour+"$"+endMinute+"$"+systemFlag+"$"+"@";
                result+=temp;
            }
            $.post('rest/appInfo',{appInfo:result},function (data) {
                if(data == "success"){
                    alert("保存成功");
                }
            })
        })
    })
})

function getSelectedHour(hour, id){

    var hourArray = new Array;
    hourArray.push("<option value='8:00'>8:00</option>  ");
    hourArray.push("<option value='9:00'>9:00</option>  ");
    hourArray.push("<option value='10:00'>10:00</option>  ");
    hourArray.push("<option value='11:00'>11:00</option>  ");
    hourArray.push("<option value='12:00'>12:00</option>  ");
    hourArray.push("<option value='13:00'>13:00</option>  ");
    hourArray.push("<option value='14:00'>14:00</option>  ");
    hourArray.push("<option value='15:00'>15:00</option>  ");
    hourArray.push("<option value='16:00'>16:00</option>  ");
    hourArray.push("<option value='17:00'>17:00</option>  ");
    hourArray.push("<option value='18:00'>18:00</option>  ");
    hourArray.push("<option value='19:00'>19:00</option>  ");
    hourArray.push("<option value='20:00'>20:00</option>  ");
    hourArray.push("<option value='21:00'>21:00</option>  ");
    hourArray.push("<option value='22:00'>22:00</option>  ");
    if("8:00" == hour){
        hourArray[0] = "<option value='8:00' selected>8:00</option>  "
    }
    if("9:00" == hour){
        hourArray[1] = "<option value='9:00' selected>9:00</option>  "
    }
    if("10:00" == hour){
        hourArray[2] = "<option value='10:00' selected>10:00</option>  "
    }
    if("11:00" == hour){
        hourArray[3] = "<option value='11:00' selected>11:00</option>  "
    }
    if("12:00" == hour){
        hourArray[4] = "<option value='12:00' selected>12:00</option>  "
    }
    if("13:00" == hour){
        hourArray[5] = "<option value='13:00' selected>13:00</option>  "
    }
    if("14:00" == hour){
        hourArray[6] = "<option value='14:00' selected>14:00</option>  "
    }
    if("15:00" == hour){
        hourArray[7] = "<option value='15:00' selected>15:00</option>  "
    }
    if("16:00" == hour){
        hourArray[8] = "<option value='16:00' selected>16:00</option>  "
    }
    if("17:00" == hour){
        hourArray[9] = "<option value='17:00' selected>17:00</option>  "
    }
    if("18:00" == hour){
        hourArray[10] = "<option value='18:00' selected>18:00</option>  "
    }
    if("19:00" == hour){
        hourArray[11] = "<option value='19:00' selected>19:00</option>  "
    }
    if("20:00" == hour){
        hourArray[12] = "<option value='20:00' selected>20:00</option>  "
    }
    if("21:00" == hour){
        hourArray[13] = "<option value='21:00' selected>21:00</option>  "
    }
    if("22:00" == hour){
        hourArray[14] = "<option value='22:00' selected>22:00</option>  "
    }
    var hourSelected = "<select id='"+id+"'>";
    for(var index in hourArray){
        hourSelected += hourArray[index];
    }
    hourSelected+="</select>"
    return hourSelected;
}

function getSelectedMinute(minute, id){

    var minuteArray = new Array;
    minuteArray.push("<option value='00'>00</option>  ");
    minuteArray.push("<option value='10'>10</option>  ");
    minuteArray.push("<option value='20'>20</option>  ");
    minuteArray.push("<option value='30'>30</option>  ");
    minuteArray.push("<option value='40'>40</option>  ");
    minuteArray.push("<option value='50'>50</option>  ");

    if("00" == minute){
        minuteArray[0] = "<option value='00' selected>00</option>  "
    }
    if("10" == minute){
        minuteArray[1] = "<option value='10' selected>10</option>  "
    }
    if("20" == minute){
        minuteArray[2] = "<option value='20' selected>20</option>  "
    }
    if("30" == minute){
        minuteArray[3] = "<option value='30' selected>30</option>  "
    }
    if("40" == minute){
        minuteArray[4] = "<option value='40' selected>40</option>  "
    }
    if("50" == minute){
        minuteArray[5] = "<option value='50' selected>50</option>  "
    }

    var minuteSelected = "<select id='"+id+"'>";
    for(var index in minuteArray){
        minuteSelected += minuteArray[index];
    }
    minuteSelected+="</select>"
    return minuteSelected;
}