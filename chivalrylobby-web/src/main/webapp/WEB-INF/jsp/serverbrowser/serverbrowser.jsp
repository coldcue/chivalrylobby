<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/module/head.jsp"%>
<%@include file="/WEB-INF/jsp/module/header.jsp"%>

<div class="container_12">
	<div class="grid_8">
		<div class="content-box">
			<h1>SERVER BROWSER</h1>
		</div>
		<div class="content-box-light">
			<div id="ID-servers">
				<ul>
					<c:forEach items="${serverList}" var="server">
						<li><a href="#">
								<table>
									<tr>
										<td class="map"><img src="static/images/maps/${server.map}.png"
											width="48" height="33" alt="${server.map.fullname}"></td>
										<td class="info">
											<div class="title">${server.name}</div>
											<div class="other">
												<img src="/static/images/flags/${server.country}.gif"
													alt="${server.country}" width="16" height="11"> ${server.ip} - ${server.map.fullname} - ${server.gamemode.fullname}
											</div>
										</td>
										<td class="players">${server.players} / ${server.slot}</td>
										<td class="join">JOIN SERVER</td>
									</tr>
								</table>
						</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="grid_4">
		<div id="ID-ad2">
			<%@include file="/WEB-INF/jsp/module/sidead.jsp"%>
		</div>
	</div>
</div>