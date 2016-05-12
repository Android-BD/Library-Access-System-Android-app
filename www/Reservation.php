

<table width="300" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
<tr>
<form name="form4" method="post" action="checkreserve.php">
<td>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
<tr>
<td colspan="3"><strong>Reservation </strong></td>
</tr>
<tr>
<td width="78">Userid</td>
<td width="6">:</td>
<td width="294"><input name="userid" type="int" id="userid"></td>
</tr>
<tr>
<td>Bookid</td>
<td>:</td>
<td><input name="bookid" type="int" id="bookid"></td>
</tr>
<tr>
<tr>
<td>Reserved By</td>
<td>:</td>
<td><input name="reservedby" type="text" id="reservedby"></td>
</tr>
<tr>
<td width="78">Reserved Date</td>
<td width="6">:</td>
<td width="294">
<input id="reserveddate" name="date" value="<?php echo date("M j, Y - g:i"); ?>"/>
</td>
</tr>

<tr>
<td width="78">Due Date</td>
<td width="6">:</td>
<td width="294">
<input id="duedate" name="date" value="<?php echo date("M j, Y - g:i"); ?>"/>
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td><input type="submit" name="Submit" value="Reserve"></td>
</tr>
</table>
</td>
</form>
</tr>
</table>