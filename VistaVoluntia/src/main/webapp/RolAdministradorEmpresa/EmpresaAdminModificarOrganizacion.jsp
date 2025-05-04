<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="VistaVoluntia.Dtos.UsuarioDtos"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Voluntia</title>
  <link rel="icon" type="image/x-icon" href="../Imagenes/favicon.ico">

<link href="/dist/output.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&amp;display=swap"
	rel="stylesheet" />
<script src="https://cdn.tailwindcss.com"></script>
<script src="//unpkg.com/alpinejs" defer></script>
</head>

<body class="bg-gray-50 dark:bg-gray-900">

	<style>
#dashboard-submenu {
	z-index: 9999;
}
</style>
	<div class="min-h-full">
		<nav class="bg-gray-800">
			<div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
				<div class="flex h-16 items-center justify-between">
					<div class="flex items-center">
						<div class="shrink-0"></div>
						<div class="hidden md:block">
							<div class="ml-10 flex items-baseline space-x-4">
								<div>
									<a
										href="../RolAdministradorEmpresa/PanelAdministradorEmpresa.html"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Inicio</a>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Usuario</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a
											href="../RolAdministradorEmpresa/EmpresaAdminNuevoUsuario.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
											usuario</a> <a
											href="../RolAdministradorEmpresa/EmpresaAdminTablaUsuarios.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											usuarios</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Servicios</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a
											href="../RolAdministradorEmpresa/EmpresaAdminNuevoServicio.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
											servicio</a> <a
											href="../RolAdministradorEmpresa/EmpresaAdminTablaServicios.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											servicios</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Emergencias</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a
											href="../RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
											emergencia</a> <a
											href="../RolAdministradorEmpresa/EmpresaAdminTablaEmergencias.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											emergencias</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Vehiculos</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a
											href="../RolAdministradorEmpresa/EmpresaAdminNuevoVehiculo.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
											vehiculo</a> <a
											href="../RolAdministradorEmpresa/EmpresaAdminTablaVehiculos.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											vehiculos</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Organizaciones</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a
											href="../RolAdministradorEmpresa/EmpresaAdminNuevaOrganizacion.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
											Organizacion</a> <a
											href="../RolAdministradorEmpresa/EmpresaAdminTablaOrganizaciones.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											organizaciones</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Planes</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a href="../RolAdministradorEmpresa/EmpresaAdminNuevoPlan.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
											plan</a> <a
											href="../RolAdministradorEmpresa/EmpresaAdminTablaPlanes.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											planes</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Claves</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a
											href="../RolAdministradorEmpresa/EmpresaAdminNuevaClave.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
											clave</a> <a
											href="../RolAdministradorEmpresa/EmpresaAdminTablaClaves.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											claves</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="hidden md:block">
						<div class="ml-4 flex items-center md:ml-6">
							<div class="relative ml-3">
								<div>
									<button type="button"
										class="relative flex max-w-xs items-center rounded-full bg-gray-800 text-sm focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 focus:outline-hidden"
										id="user-menu-button" aria-expanded="false"
										aria-haspopup="true">
										<span class="absolute -inset-1.5"></span> <span
											class="sr-only">Open user menu</span> <img
											class="size-8 rounded-full" id="imagenUsuarioPerfil"
											src="https://us.123rf.com/450wm/thesomeday123/thesomeday1231712/thesomeday123171200009/91087331-icono-de-perfil-de-avatar-predeterminado-para-hombre-marcador-de-posici%C3%B3n-de-foto-gris-vector-de.jpg?ver=6"
											alt="Imagen de Perfil del usuario">
									</button>
								</div>

								<div
									class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 ring-1 shadow-lg ring-black/5 focus:outline-hidden"
									role="menu" aria-orientation="vertical"
									aria-labelledby="user-menu-button" tabindex="-1">
									<a
										href="../RolAdministradorEmpresa/EmpresaAdminPerfilUsuario.jsp"
										class="block px-4 py-2 text-sm text-gray-700" role="menuitem"
										tabindex="-1" id="user-menu-item-0">Perfil</a> <a href="#"
										class="block px-4 py-2 text-sm text-gray-700" role="menuitem"
										tabindex="-1" id="user-menu-item-2">Cerrar</a>
								</div>
							</div>
						</div>
					</div>
					<div class="-mr-2 flex md:hidden">
						<button type="button"
							class="relative inline-flex items-center justify-center rounded-md bg-gray-800 p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 focus:outline-hidden"
							aria-controls="mobile-menu" aria-expanded="false">
							<span class="absolute -inset-0.5"></span> <span class="sr-only">Open
								main menu</span>
							<svg class="block size-6" fill="none" viewBox="0 0 24 24"
								stroke-width="1.5" stroke="currentColor" aria-hidden="true"
								data-slot="icon">
                                            <path stroke-linecap="round"
									stroke-linejoin="round"
									d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                                        </svg>
							<svg class="hidden size-6" fill="none" viewBox="0 0 24 24"
								stroke-width="1.5" stroke="currentColor" aria-hidden="true"
								data-slot="icon">
                                            <path stroke-linecap="round"
									stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
                                        </svg>
						</button>
					</div>
				</div>
			</div>

			<div class="md:hidden" id="mobile-menu">
				<div class="space-y-1 px-2 pt-2 pb-3 sm:px-3">
					<div>
						<a
							href="../RolAdministradorEmpresa/PanelAdministradorEmpresa.html"
							class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Inicio</a>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Usuarios</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2 z-50"
							id="dashboard-submenu">
							<a href="../RolAdministradorEmpresa/EmpresaAdminNuevoUsuario.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								usuario</a> <a
								href="../RolAdministradorEmpresa/EmpresaAdminTablaUsuarios.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								usuarios</a>
						</div>

					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Servicios</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
							id="dashboard-submenu">
							<a
								href="../RolAdministradorEmpresa/EmpresaAdminNuevoServicio.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								servicio</a> <a
								href="../RolAdministradorEmpresa/EmpresaAdminTablaServicios.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								servicios</a>
						</div>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Emergencias</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
							id="dashboard-submenu">
							<a
								href="../RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								emergencias</a> <a
								href="../RolAdministradorEmpresa/EmpresaAdminTablaEmergencias.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								emergencias</a>
						</div>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Vehiculos</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
							id="dashboard-submenu">
							<a
								href="../RolAdministradorEmpresa/EmpresaAdminNuevoVehiculo.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								vehiculo</a> <a
								href="../RolAdministradorEmpresa/EmpresaAdminTablaVehiculos.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								vehiculos</a>
						</div>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Organizaciones</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
							id="dashboard-submenu">
							<a
								href="../RolAdministradorEmpresa/EmpresaAdminNuevaOrganizacion.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
								organizacion</a> <a
								href="../RolAdministradorEmpresa/EmpresaAdminTablaOrganizaciones.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								organizaciones</a>
						</div>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Planes</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
							id="dashboard-submenu">
							<a href="../RolAdministradorEmpresa/EmpresaAdminNuevoPlan.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								plan</a> <a
								href="../RolAdministradorEmpresa/EmpresaAdminTablaPlanes.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								planes</a>
						</div>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Claves</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
							id="dashboard-submenu">
							<a href="#"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
								clave</a> <a href="#"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								claves</a>
						</div>
					</div>
				</div>
			</div>
		</nav>
		<header class="bg-white shadow-sm">
			<div
				class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8 flex justify-between items-center">
				<h1 class="text-3xl font-bold tracking-tight text-gray-900">Modificar
					organizacion</h1>
				<a href="../RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp"
					class="inline-flex items-center px-6 py-3 text-lg font-semibold text-white bg-red-600 rounded-lg shadow-lg hover:bg-red-700 focus:outline-none focus:ring-4 focus:ring-red-400 transition">
					<svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor"
						stroke-width="2" viewBox="0 0 24 24"
						xmlns="http://www.w3.org/2000/svg">
                                    <path stroke-linecap="round"
							stroke-linejoin="round"
							d="M12 8v4m0 4h.01M4.93 19h14.14a2 2 0 001.73-3L13.73 4a2 2 0 00-3.46 0L3.2 16a2 2 0 001.73 3z">
                                    </path>
                                </svg> Lanzar Emergencia
				</a>
			</div>
		</header>
		<main>
			<div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
				<div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
					<x-placeholder>
					<div class="mx-auto w-full bg-white p-6 shadow rounded-xl">
						<div id="loader"
							class="fixed inset-0 flex items-center justify-center bg-gray-700 bg-opacity-50 hidden z-50">
							<div
								class="w-16 h-16 border-4 border-blue-500 border-dashed rounded-full animate-spin">
							</div>
						</div>

<form class="needs-validation" novalidate
      action="/VistaVoluntia/organizacion" method="post"
      enctype="multipart/form-data" id="organizacionForm">

    <input type="hidden" name="accion" value="modificar" />
    <input type="hidden" id="idOrganizacion" name="idOrganizacion"
           value="<%= request.getParameter("idOrganizacion") != null
                     ? request.getParameter("idOrganizacion")
                     : "" %>" />

    <div class="-mx-3 flex flex-wrap">
        <div class="w-full px-3 sm:w-1/2">
            <div class="mb-5">
                <label for="nombreOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    Nombre Institución
                </label>
                <input type="text" name="nombreOrganizacion" id="nombreOrganizacion"
                       value="<%= request.getParameter("nombreOrganizacion") != null
                                 ? request.getParameter("nombreOrganizacion")
                                 : "" %>"
                       placeholder="Nombre Institución" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="nombreError">El nombre es obligatorio</p>
            </div>
        </div>
        <div class="w-full px-3 sm:w-1/2">
            <div class="mb-5">
                <label for="nifOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    NIF / CIF
                </label>
                <input type="text" name="nifOrganizacion" id="nifOrganizacion"
                       value="<%= request.getParameter("nifOrganizacion") != null
                                 ? request.getParameter("nifOrganizacion")
                                 : "" %>"
                       placeholder="NIF / CIF" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="nifError">El NIF es obligatorio</p>
            </div>
        </div>
    </div>

    <div class="-mx-3 flex flex-wrap">
        <div class="w-full px-3 sm:w-1/2">
            <div class="mb-5">
                <label for="telefonoOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    Teléfono
                </label>
                <input type="tel" name="telefonoOrganizacion" id="telefonoOrganizacion"
                       value="<%= request.getParameter("telefonoOrganizacion") != null
                                 ? request.getParameter("telefonoOrganizacion")
                                 : "" %>"
                       placeholder="Teléfono" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="telefonoError">El teléfono es obligatorio</p>
            </div>
        </div>
        <div class="w-full px-3 sm:w-1/2">
            <div class="mb-5">
                <label for="emailOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    Correo Electrónico
                </label>
                <input type="email" name="emailOrganizacion" id="emailOrganizacion"
                       value="<%= request.getParameter("emailOrganizacion") != null
                                 ? request.getParameter("emailOrganizacion")
                                 : "" %>"
                       placeholder="correo@institucion.com" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="emailError">El correo es obligatorio</p>
            </div>
        </div>
    </div>

    <div class="-mx-3 flex flex-wrap">
        <div class="w-full px-3 sm:w-1/2">
            <div class="mb-5">
                <label for="ciudadOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    Ciudad
                </label>
                <input type="text" name="ciudadOrganizacion" id="ciudadOrganizacion"
                       value="<%= request.getParameter("ciudadOrganizacion") != null
                                 ? request.getParameter("ciudadOrganizacion")
                                 : "" %>"
                       placeholder="Ciudad" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="ciudadError">La ciudad es obligatoria</p>
            </div>
        </div>
        <div class="w-full px-3 sm:w-1/2">
            <div class="mb-5">
                <label for="provinciaOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    Provincia
                </label>
                <input type="text" name="provinciaOrganizacion" id="provinciaOrganizacion"
                       value="<%= request.getParameter("provinciaOrganizacion") != null
                                 ? request.getParameter("provinciaOrganizacion")
                                 : "" %>"
                       placeholder="Provincia" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="provinciaError">La provincia es obligatoria</p>
            </div>
        </div>
    </div>

    <div class="flex flex-wrap w-full">
        <div class="w-full">
            <div class="mb-5">
                <label for="direccionOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    Dirección
                </label>
                <input type="text" name="direccionOrganizacion" id="direccionOrganizacion"
                       value="<%= request.getParameter("direccionOrganizacion") != null
                                 ? request.getParameter("direccionOrganizacion")
                                 : "" %>"
                       placeholder="Dirección" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="direccionError">La dirección es obligatoria</p>
            </div>
        </div>
    </div>

    <div class="-mx-3 flex flex-wrap">
        <div class="w-full px-3 sm:w-1/2">
            <div class="mb-5">
                <label for="tipoInstitucionOrganizacion" class="mb-3 block text-base font-medium text-[#07074D]">
                    Tipo de Institución
                </label>
                <input type="text" name="tipoInstitucionOrganizacion" id="tipoInstitucionOrganizacion"
                       value="<%= request.getParameter("tipoInstitucionOrganizacion") != null
                                 ? request.getParameter("tipoInstitucionOrganizacion")
                                 : "" %>"
                       placeholder="Tipo de Institución" required
                       class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                <p class="text-red-500 text-sm mt-2 hidden" id="tipoError">El tipo de institución es obligatorio</p>
            </div>
        </div>
    </div>

    <div class="mt-6 flex items-center justify-end gap-x-6">
        <button type="button" class="text-sm/6 font-semibold text-gray-900">Cancelar</button>
        <button type="submit" class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Guardar
        </button>
    </div>
</form>


					</div>
				</div>
			</div>
	</div>


	<script>
                    document.addEventListener('DOMContentLoaded', () => {
                        const cerrarLink = document.getElementById('user-menu-item-2');
                        if (cerrarLink) {
                            cerrarLink.addEventListener('click', function (event) {
                                event.preventDefault();
                                fetch("http://localhost:8080/VistaVoluntia/usuario", {
                                    method: "POST",
                                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                                    body: "accion=cerrar+sesion"
                                })
                                    .then(response => {
                                        window.location.href = "http://localhost:8080/VistaVoluntia/";
                                    })
                                    .catch(error => {
                                        console.error("Error al cerrar sesión:", error);
                                        window.location.href = "http://localhost:8080/VistaVoluntia/";
                                    });
                            });
                        }
                    });
                </script>
<script>
document.addEventListener('DOMContentLoaded', () => {
  const status  = "<%= request.getAttribute("status") != null ? request.getAttribute("status") : "" %>";
  const mensaje = "<%= request.getAttribute("mensaje") != null ? request.getAttribute("mensaje") : "" %>";
  if (mensaje !== "") {
    const container = document.createElement('div');
    container.style = 'position:fixed;bottom:20px;right:20px;z-index:1000';
    container.innerHTML = status === 'success'
      ? `<div class="flex w-full max-w-sm overflow-hidden bg-white rounded-lg shadow-md">
           <div class="flex items-center justify-center w-12 bg-emerald-500">
             <svg class="w-6 h-6 text-white fill-current" viewBox="0 0 40 40">
               <path d="M20 3.33331C10.8 3.33331 …Z"/>
             </svg>
           </div>
           <div class="px-4 py-2 -mx-3">
             <div class="mx-3">
               <span class="font-semibold text-emerald-500">Éxito</span>
               <p class="text-sm text-gray-600">${mensaje}</p>
             </div>
           </div>
         </div>`
      : `<div class="flex w-full max-w-sm overflow-hidden bg-white rounded-lg shadow-md">
           <div class="flex items-center justify-center w-12 bg-red-500">
             <svg class="w-6 h-6 text-white fill-current" viewBox="0 0 40 40">
               <path d="M20 3.36667C10.8167 3.36667 …Z"/>
             </svg>
           </div>
           <div class="px-4 py-2 -mx-3">
             <div class="mx-3">
               <span class="font-semibold text-red-500">Error</span>
               <p class="text-sm text-gray-600">${mensaje}</p>
             </div>
           </div>
         </div>`;
    document.body.appendChild(container);
    setTimeout(() => container.remove(), 3000);
  }
});
</script>


	<script>
                    document.addEventListener('DOMContentLoaded', () => {
                        const formData = new URLSearchParams();
                        formData.append("accion", "sesion");

                        fetch("http://localhost:8080/VistaVoluntia/login", {
                            method: "POST",
                            credentials: "include",
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded"
                            },
                            body: formData.toString()
                        })
                            .then(response => response.json())
                            .then(data => {
                                if (data.error) {
                                    console.error("Error:", data.error);
                                    return;
                                }
                                if (data.imagenUsuarioPerfil && data.imagenUsuarioPerfil !== "") {
                                    document.getElementById("imagenUsuarioPerfil").src = "data:image/png;base64," + data.imagenUsuarioPerfil;
                                } else {
                                    document.getElementById("imagenUsuarioPerfil").alt = "No se ha proporcionado una imagen de perfil.";
                                }
                            })
                            .catch(error => {
                                console.error("Error al obtener la imagen del usuario:", error);
                            });
                    });
                </script>

	

	<script>
                    const loadFile = function (event) {
                        const preview = document.getElementById('previewImage');
                        preview.src = URL.createObjectURL(event.target.files[0]);
                    };
                </script>

	<script>
                    document.addEventListener('DOMContentLoaded', () => {
                        const userMenuButton = document.getElementById('user-menu-button');
                        const userMenu = document.querySelector('[aria-labelledby="user-menu-button"]');
                        const mobileMenuButton = document.querySelector('[aria-controls="mobile-menu"]');
                        const mobileMenu = document.getElementById('mobile-menu');

                        if (userMenuButton && userMenu) {
                            userMenu.classList.add('hidden');
                            userMenuButton.setAttribute('aria-expanded', 'false');
                            userMenuButton.addEventListener('click', () => {
                                const isExpanded = userMenuButton.getAttribute('aria-expanded') === 'true';
                                userMenuButton.setAttribute('aria-expanded', !isExpanded);
                                userMenu.classList.toggle('hidden');
                            });
                            document.addEventListener('click', (event) => {
                                if (!userMenu.contains(event.target) && !userMenuButton.contains(event.target)) {
                                    userMenu.classList.add('hidden');
                                    userMenuButton.setAttribute('aria-expanded', 'false');
                                }
                            });
                        }

                        if (mobileMenuButton && mobileMenu) {
                            mobileMenu.classList.add('hidden');
                            mobileMenuButton.setAttribute('aria-expanded', 'false');
                            mobileMenuButton.addEventListener('click', () => {
                                const isExpanded = mobileMenuButton.getAttribute('aria-expanded') === 'true';
                                mobileMenuButton.setAttribute('aria-expanded', !isExpanded);
                                mobileMenu.classList.toggle('hidden');
                            });
                        }

                        const menuOptions = document.querySelectorAll('.relative');
                        menuOptions.forEach(option => {
                            const subMenu = option.querySelector('.absolute');
                            let timeout;
                            option.addEventListener('mouseenter', () => {
                                clearTimeout(timeout);
                                subMenu.classList.remove('hidden');
                            });
                            option.addEventListener('mouseleave', (event) => {
                                if (!subMenu.contains(event.relatedTarget)) {
                                    timeout = setTimeout(() => {
                                        subMenu.classList.add('hidden');
                                    }, 100);
                                }
                            });
                            subMenu.addEventListener('mouseenter', () => {
                                clearTimeout(timeout);
                                subMenu.classList.remove('hidden');
                            });
                            subMenu.addEventListener('mouseleave', (event) => {
                                if (!option.contains(event.relatedTarget)) {
                                    timeout = setTimeout(() => {
                                        subMenu.classList.add('hidden');
                                    }, 200);
                                }
                            });
                            option.addEventListener('click', (event) => {
                                const isExpanded = subMenu.classList.contains('hidden');
                                subMenu.classList.toggle('hidden', !isExpanded);
                            });
                        });

                        const menuItems = document.querySelectorAll('.ml-10 a');
                        menuItems.forEach(item => {
                            item.addEventListener('click', (event) => {
                                menuItems.forEach(link => link.classList.remove('bg-gray-900', 'text-white'));
                                item.classList.add('bg-gray-900', 'text-white');
                            });
                        });

                        document.addEventListener('click', (event) => {
                            const subMenus = document.querySelectorAll('.absolute');
                            subMenus.forEach(subMenu => {
                                if (!subMenu.contains(event.target) && !event.target.closest('.relative')) {
                                    subMenu.classList.add('hidden');
                                }
                            });
                        });
                    });
                </script>
</body>

</html>