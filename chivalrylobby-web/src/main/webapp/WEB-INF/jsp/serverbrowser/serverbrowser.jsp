<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/module/head.jsp"%>
<%@include file="/WEB-INF/jsp/module/header.jsp"%>

<script type="text/javascript">
<!--
	function showIp(ip) {
		prompt('Copy the address! CTRL-C', ip);
	}
//-->
</script>

<div class="container_12">
	<div class="grid_8">
		<div class="content-box">
			<h1>SERVER BROWSER</h1>
		</div>
		<div class="content-box-light">
			<div id="ID-servers">
				<c:choose>
					<c:when test="${serversCount!=0}">
						<ul>
							<c:forEach items="${serverList}" var="server">
								<li><a
									href="javascript:showIp('${server.ip}:${server.port}');">
										<table>
											<tr>
												<td class="map"><img
													src="static/images/maps/${server.map}.png" width="48"
													height="33" alt="${server.map.fullname}"></td>
												<td class="info">
													<div class="title">${server.name}</div>
													<div class="other">
														<img src="/static/images/flags/${server.country}.gif"
															alt="${server.country}" width="16" height="11">
														${server.ip}:${server.port} - ${server.map.fullname} -
														${server.gamemode.fullname}
														<c:if test="${server.tunngle}"> - Tunngle</c:if>
													</div>
												</td>
												<td class="players">${server.players} / ${server.slot}</td>
												<td class="join">JOIN SERVER</td>
											</tr>
										</table>
								</a></li>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<h2>There are no online servers at this moment.</h2>
						<p class="footnote" style="font-size: 13px;">
							Create a server by yourself! It is very easy and you can collect
							many players! <a href="/howto/createserver">Click here!</a>
						</p>
					</c:otherwise>
				</c:choose>

				<p class="footnote">The server list is not refreshing
					automatically! You have to refresh it manually!</p>
			</div>
		</div>
	</div>
	<div class="grid_4">
		<div id="ID-ad2">
			<%@include file="/WEB-INF/jsp/module/sidead.jsp"%>
		</div>
	</div>
</div>