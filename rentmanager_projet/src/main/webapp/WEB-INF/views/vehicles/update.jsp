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
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <form class="form-horizontal" method="post" action ="${pageContext.request.contextPath}/cars/update?id=${vehicle.id}" >
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="manufacturer" class="col-sm-2 control-label">Marque</label>
                                    <div class="col-sm-10">
                                        <input type="text" value ="${vehicle.constructeur}" class="form-control" id="manufacturer" name="manufacturer" placeholder="Marque" required>
                                    </div>
                                </div>
                               
                                <div class="form-group">
                                    <label for="nbplaces" class="col-sm-2 control-label">Nombre de places</label>

                                    <div class="col-sm-10">
                                        <input type="text" value ="${vehicle.nbplaces}" class="form-control" id="nbplaces" name="nbplaces" placeholder="Nombre de places" required>
                                    </div>
                                </div>
                                <!--
                                <div class="form-group">
                                    <label for="owner" class="col-sm-2 control-label">Propriétaire</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="owner" name="owner">
                                            <option value="1">John Doe</option>
                                            <option value="2">Jane Doe</option>
                                        </select>
                                    </div>
                                </div>
                                -->
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
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
s