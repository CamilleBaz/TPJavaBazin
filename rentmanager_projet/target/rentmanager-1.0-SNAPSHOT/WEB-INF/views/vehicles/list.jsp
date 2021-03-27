<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Voitures
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/cars/create">ajouter</a>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Constructeur</th>
                               
                                    <th>Nombre de places</th>
                                    <th>Action</th>
                                </tr>
                                
                                <c:forEach items="${vehicles}" var="vehicle">
	                                <tr>
	                                <!-- Popup Modal -->
										<div class="modal fade" id="supprModal${vehicle.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
										  <div class="modal-dialog" role="document">
										    <div class="modal-content">
										      <div class="modal-header">
										        <h5 class="modal-title" id="exampleModalLabel">Confirmation de suppression</h5>
										      </div>
										      <div class="modal-body">
										        Supprimer le véhicule ?
										      </div>
										      <div class="modal-footer">
										        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
										        <a class="btn btn-primary" href="${pageContext.request.contextPath}/cars/delete?id=${vehicle.id}">Confirmer</a>
										      </div>
										    </div>
										  </div>
										</div>
										
	                                    <td>${vehicle.id}.</td>
	                                    <td>${vehicle.constructeur}</td>
	                            
	                                    <td>${vehicle.nbplaces}</td>
	                                    
	                                    <td>
	                                        <a class="btn btn-primary " href="${pageContext.request.contextPath}/cars/details?id=${vehicle.id}">
	                                            <i class="fa fa-play"></i>
	                                        </a>
	                                        <a class="btn btn-success " href="${pageContext.request.contextPath}/cars/update?id=${vehicle.id}">
	                                            <i class="fa fa-edit"></i>
	                                        </a>
	                                        <a class="btn btn-danger" data-toggle="modal" data-target="#supprModal${vehicle.id}">
											  	<i class="fa fa-trash"></i>
											</a>
	                                        <!-- <a class="btn btn-danger " href="${pageContext.request.contextPath}/cars/delete?id=${vehicle.id}">
	                                            <i class="fa fa-trash"></i>
	                                        </a> -->
	                                    </td>
	                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>

