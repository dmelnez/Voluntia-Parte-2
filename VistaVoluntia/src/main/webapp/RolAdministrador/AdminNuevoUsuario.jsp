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
									<a href="../RolAdministrador/PanelAdministrador.html"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Inicio</a>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Usuario</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a href="../RolAdministrador/AdminNuevoUsuario.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
											usuario</a> <a href="../RolAdministrador/AdminTablaUsuarios.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											usuarios</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Servicios</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a href="../RolAdministrador/AdminNuevoServicio.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
											servicio</a> <a
											href="../RolAdministrador/AdminTablaServicios.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											servicios</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Emergencias</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a href="../RolAdministrador/AdminNuevaEmergencia.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
											emergencia</a> <a
											href="../RolAdministrador/AdminTablaEmergencias.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											emergencias</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Vehiculos</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a href="../RolAdministrador/AdminNuevoVehiculo.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
											vehiculo</a> <a
											href="../RolAdministrador/AdminTablaVehiculos.html"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
											vehiculos</a>
									</div>
								</div>
								<div class="relative">
									<a href="#"
										class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Claves</a>
									<div
										class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
										<a href="../RolAdministrador/AdminNuevaClave.jsp"
											class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
											clave</a> <a href="../RolAdministrador/AdminTablaClaves.html"
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
									<a href="../RolAdministrador/AdminPerfilUsuario.jsp"
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
                  <path stroke-linecap="round" stroke-linejoin="round"
									d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                </svg>
							<svg class="hidden size-6" fill="none" viewBox="0 0 24 24"
								stroke-width="1.5" stroke="currentColor" aria-hidden="true"
								data-slot="icon">
                  <path stroke-linecap="round" stroke-linejoin="round"
									d="M6 18 18 6M6 6l12 12" />
                </svg>
						</button>
					</div>
				</div>
			</div>

			<div class="md:hidden" id="mobile-menu">
				<div class="space-y-1 px-2 pt-2 pb-3 sm:px-3">
					<div>
						<a href="../RolAdministrador/PanelAdministrador.html"
							class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Inicio</a>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Usuarios</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2 z-50"
							id="dashboard-submenu">
							<a href="../RolAdministrador/AdminNuevoUsuario.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								usuario</a> <a href="../RolAdministrador/AdminTablaUsuarios.html"
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
							<a href="../RolAdministrador/AdminNuevoServicio.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								servicio</a> <a href="../RolAdministrador/AdminTablaServicios.html"
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
							<a href="../RolAdministrador/AdminNuevaEmergencia.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								emergencias</a> <a
								href="../RolAdministrador/AdminTablaEmergencias.html"
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
							<a href="../RolAdministrador/AdminNuevoVehiculo.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
								vehiculo</a> <a href="../RolAdministrador/AdminTablaVehiculos.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								vehiculos</a>
						</div>
					</div>
					<div class="relative">
						<a href="#"
							class="block rounded-md px-3 py-2 text-base font-medium text-white"
							aria-current="page" id="dashboard-menu">Claves</a>
						<div
							class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
							id="dashboard-submenu">
							<a href="../RolAdministrador/AdminNuevaClave.jsp"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
								clave</a> <a href="../RolAdministrador/AdminTablaClaves.html"
								class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
								claves</a>
						</div>
					</div>
				</div>
			</div>
	</div>
	</nav>
	<header class="bg-white shadow-sm">
		<div
			class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8 flex justify-between items-center">
			<h1 class="text-3xl font-bold tracking-tight text-gray-900">Nuevo
				Usuario</h1>
			<a href="../RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp"
				class="inline-flex items-center px-6 py-3 text-lg font-semibold text-white bg-red-600 rounded-lg shadow-lg hover:bg-red-700 focus:outline-none focus:ring-4 focus:ring-red-400 transition">
				<svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor"
					stroke-width="2" viewBox="0 0 24 24"
					xmlns="http://www.w3.org/2000/svg">
        <path stroke-linecap="round" stroke-linejoin="round"
						d="M12 8v4m0 4h.01M4.93 19h14.14a2 2 0 001.73-3L13.73 4a2 2 0 00-3.46 0L3.2 16a2 2 0 001.73 3z"></path>
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
							class="w-16 h-16 border-4 border-blue-500 border-dashed rounded-full animate-spin"></div>
					</div>

					<form class="needs-validation" novalidate
						action="/VistaVoluntia/usuario" method="post"
						enctype="multipart/form-data" id="usuarioForm">
						<input type="hidden" name="accion" value="aniadir" />
						<div class="-mx-3 flex flex-wrap">
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="nombreUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Nombre </label> <input type="text" name="nombreUsuario"
										id="nombreUsuario" placeholder="Nombre" required
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
									<p class="text-red-500 text-sm mt-2 hidden" id="nombreError">El
										nombre es obligatorio</p>
								</div>
							</div>
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="apellidosUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Apellidos </label> <input type="text" name="apellidosUsuario"
										id="apellidosUsuario" placeholder="Apellidos" required
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
									<p class="text-red-500 text-sm mt-2 hidden" id="apellidosError">Los
										apellidos son obligatorios</p>
								</div>
							</div>
						</div>

						<div class="-mx-3 flex flex-wrap">
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="dniUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										DNI / NIE </label> <input type="text" name="dniUsuario"
										id="dniUsuario"
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
										placeholder="DNI / NIE" />
								</div>
							</div>
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="fechaNacimientoUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Fecha de Nacimiento </label> <input type="date"
										name="fechaNacimientoUsuario" id="fechaNacimientoUsuario"
										required
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md" />
									<p class="text-red-500 text-sm mt-2 hidden" id="fechaError">La
										fecha de nacimiento es obligatoria</p>
								</div>
							</div>
						</div>

						<div class="flex flex-wrap w-full">
							<div class="w-full">
								<div class="mb-5">
									<label for="indicativoUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Indicativo </label> <input type="text" name="indicativoUsuario"
										id="indicativoUsuario" placeholder="Indicativo"
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md" />
								</div>
							</div>
						</div>

						<div class="flex items-center gap-4">
							<div class="w-1/2">
								<label for="generoUsuario"
									class="block text-gray-700 font-medium mb-2">Género</label> <select
									id="generoUsuario" name="generoUsuario"
									class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md"
									required>
									<option value="">Seleccionar Género</option>
									<option value="Hombre">Hombre</option>
									<option value="Mujer">Mujer</option>
									<option value="Otro">Otro</option>
								</select>
							</div>
							<div class="w-1/2">
								<label for="rolUsuario"
									class="block text-gray-700 font-medium mb-2">Tipo de
									usuario</label> <select id="rolUsuario" name="rolUsuario"
									class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md"
									required>
									<option value="">Privilegios</option>
									<option value="admin">Administrador</option>
									<option value="Voluntario">Voluntario</option>
								</select>
							</div>
						</div>

						<div class="mb-5 pt-3">
							<label
								class="mb-5 block text-base font-semibold text-[#07074D] sm:text-xl">
								Perfil del usuario </label>
							<div class="flex items-center space-x-6">
								<div class="shrink-0">
									<img id="previewImage"
										class="h-16 w-16 object-cover rounded-full"
										src="https://static.vecteezy.com/system/resources/previews/026/530/210/non_2x/modern-person-icon-user-and-anonymous-icon-vector.jpg" />
								</div>
								<label class="block"> <span class="sr-only">Choose
										profile photo</span> <input type="file" onchange="loadFile(event)"
									id="imagenPerfilUsuario" name="imagenPerfilUsuario"
									class="block w-full text-sm text-slate-500
              file:mr-4 file:py-2 file:px-4
              file:rounded-full file:border-0
              file:text-sm file:font-semibold
              file:bg-violet-50 file:text-violet-700
              hover:file:bg-violet-100" />
								</label>
							</div>
						</div>

						<hr>

						<div class="mb-5 pt-3">
							<label
								class="mb-5 block text-base font-semibold text-[#07074D] sm:text-xl">
								Contacto </label>
						</div>

						<div class="-mx-3 flex flex-wrap">
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="codigoPostalUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Codigo Postal </label> <input type="text" name="codigoPostalUsuario"
										id="codigoPostalUsuario" placeholder="Codigo Postal"
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md" />
								</div>
							</div>
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="localidadUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Localidad </label> <input type="text" name="localidadUsuario"
										id="localidadUsuario" placeholder="Localidad"
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md" />
								</div>
							</div>
						</div>

						<div class="-mx-3 flex flex-wrap">
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="telefonoUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Telefono </label> <input type="text" name="telefonoUsuario"
										id="telefonoUsuario" placeholder="Telefono"
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md" />
								</div>
							</div>
							<div class="w-full px-3 sm:w-1/2">
								<div class="mb-5">
									<label for="provinciaUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Provincias de España </label> <select id="provinciaUsuario"
										name="provinciaUsuario"
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md"
										required>
										<option value="" disabled selected>Seleccione una
											provincia</option>
										<option value="Álava">Álava</option>
										<option value="Albacete">Albacete</option>
										<option value="Alicante">Alicante</option>
										<option value="Almería">Almería</option>
										<option value="Asturias">Asturias</option>
										<option value="Ávila">Ávila</option>
										<option value="Badajoz">Badajoz</option>
										<option value="Barcelona">Barcelona</option>
										<option value="Burgos">Burgos</option>
										<option value="Cáceres">Cáceres</option>
										<option value="Cádiz">Cádiz</option>
										<option value="Cantabria">Cantabria</option>
										<option value="Castellón">Castellón</option>
										<option value="Ciudad Real">Ciudad Real</option>
										<option value="Córdoba">Córdoba</option>
										<option value="Cuenca">Cuenca</option>
										<option value="Girona">Girona</option>
										<option value="Granada">Granada</option>
										<option value="Guadalajara">Guadalajara</option>
										<option value="Guipúzcoa">Guipúzcoa</option>
										<option value="Huelva">Huelva</option>
										<option value="Huesca">Huesca</option>
										<option value="Illes Balears">Illes Balears</option>
										<option value="Jaén">Jaén</option>
										<option value="La Coruña">La Coruña</option>
										<option value="La Rioja">La Rioja</option>
										<option value="Las Palmas">Las Palmas</option>
										<option value="León">León</option>
										<option value="Lleida">Lleida</option>
										<option value="Lugo">Lugo</option>
										<option value="Madrid">Madrid</option>
										<option value="Málaga">Málaga</option>
										<option value="Murcia">Murcia</option>
										<option value="Navarra">Navarra</option>
										<option value="Ourense">Ourense</option>
										<option value="Palencia">Palencia</option>
										<option value="Pontevedra">Pontevedra</option>
										<option value="Salamanca">Salamanca</option>
										<option value="Segovia">Segovia</option>
										<option value="Sevilla">Sevilla</option>
										<option value="Soria">Soria</option>
										<option value="Tarragona">Tarragona</option>
										<option value="Santa Cruz de Tenerife">Santa Cruz de
											Tenerife</option>
										<option value="Teruel">Teruel</option>
										<option value="Toledo">Toledo</option>
										<option value="Valencia">Valencia</option>
										<option value="Valladolid">Valladolid</option>
										<option value="Vizcaya">Vizcaya</option>
										<option value="Zamora">Zamora</option>
										<option value="Zaragoza">Zaragoza</option>
										<option value="Ceuta">Ceuta</option>
										<option value="Melilla">Melilla</option>
									</select>
								</div>
							</div>
						</div>

						<div class="flex flex-wrap w-full">
							<div class="w-full">
								<div class="mb-5">
									<label for="direccionUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Direccion </label> <input type="text" name="direccionUsuario"
										id="direccionUsuario" placeholder="Direccion"
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md" />
								</div>
							</div>
						</div>

						<hr>

						<div class="mb-5 pt-3">
							<label
								class="mb-5 block text-base font-semibold text-[#07074D] sm:text-xl">
								Inicio de sesión </label>
						</div>

						<div class="flex flex-wrap w-full">
							<div class="w-full">
								<div class="mb-5">
									<label for="correoUsuario"
										class="mb-3 block text-base font-medium text-[#07074D]">
										Correo Electrónico </label> <input type="email" name="correoUsuario"
										id="correoUsuario" placeholder="Correo Electrónico" required
										class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#A64F1] focus:shadow-md" />
									<p id="emailError" class="text-red-500 text-sm mt-2 hidden">El
										correo debe contener @</p>
								</div>
							</div>
						</div>

						<div class="mt-6 flex items-center justify-end gap-x-6">
							<button type="button"
								class="text-sm/6 font-semibold text-gray-900">Cancelar</button>
							<button type="submit"
								class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Guardar</button>
						</div>
					</form>

				</div>
			</div>
		</div>
		</div>


		<script src="//unpkg.com/alpinejs" defer></script>
		<script src="https://cdn.tailwindcss.com"></script>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<script>
  document.addEventListener('DOMContentLoaded', () => {
    const cerrarLink = document.getElementById('user-menu-item-2');
    if(cerrarLink) {
      cerrarLink.addEventListener('click', function(event) {
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
    const status = "<%= request.getAttribute("status") %>";
    const mensaje = "<%= request.getAttribute("mensaje") %>";
    
    if(mensaje) {
      const container = document.createElement('div');
      container.style.position = 'fixed';
      container.style.bottom = '20px';
      container.style.right = '20px';
      container.style.zIndex = '1000';

      let notificationHTML = "";
      if (status === 'success') {
          notificationHTML = `
              <div class="flex w-full max-w-sm overflow-hidden bg-white rounded-lg shadow-md dark:bg-gray-800">
                  <div class="flex items-center justify-center w-12 bg-emerald-500">
                      <svg class="w-6 h-6 text-white fill-current" viewBox="0 0 40 40" xmlns="http://www.w3.org/2000/svg">
                          <path d="M20 3.33331C10.8 3.33331 3.33337 10.8 3.33337 20C3.33337 29.2 10.8 36.6666 20 36.6666C29.2 36.6666 36.6667 29.2 36.6667 20C36.6667 10.8 29.2 3.33331 20 3.33331ZM16.6667 28.3333L8.33337 20L10.6834 17.65L16.6667 23.6166L29.3167 10.9666L31.6667 13.3333L16.6667 28.3333Z" />
                      </svg>
                  </div>
                  <div class="px-4 py-2 -mx-3">
                      <div class="mx-3">
                          <span class="font-semibold text-emerald-500 dark:text-emerald-400">Éxito</span>
                          <p class="text-sm text-gray-600 dark:text-gray-200">${mensaje}</p>
                      </div>
                  </div>
              </div>
          `;
      } else if (status === 'error') {
          notificationHTML = `
              <div class="flex w-full max-w-sm overflow-hidden bg-white rounded-lg shadow-md dark:bg-gray-800">
                  <div class="flex items-center justify-center w-12 bg-red-500">
                      <svg class="w-6 h-6 text-white fill-current" viewBox="0 0 40 40" xmlns="http://www.w3.org/2000/svg">
                          <path d="M20 3.36667C10.8167 3.36667 3.3667 10.8167 3.3667 20C3.3667 29.1833 10.8167 36.6333 20 36.6333C29.1834 36.6333 36.6334 29.1833 36.6334 20C36.6334 10.8167 29.1834 3.36667 20 3.36667ZM19.1334 33.3333V22.9H13.3334L21.6667 6.66667V17.1H27.25L19.1334 33.3333Z" />
                      </svg>
                  </div>
                  <div class="px-4 py-2 -mx-3">
                      <div class="mx-3">
                          <span class="font-semibold text-red-500 dark:text-red-400">Error</span>
                          <p class="text-sm text-gray-600 dark:text-gray-200">${mensaje}</p>
                      </div>
                  </div>
              </div>
          `;
      }
      
      container.innerHTML = notificationHTML;
      document.body.appendChild(container);
      
      setTimeout(() => {
          container.remove();
      }, 3000);
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
      if(data.error) {
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
  document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('usuarioForm');
    const submitButton = form.querySelector('button[type="submit"]');
    const loader = document.getElementById('loader');

    form.addEventListener('submit', function(e) {
      e.preventDefault();

      let valid = true;
      const nombre = document.getElementById('nombreUsuario');
      const apellidos = document.getElementById('apellidosUsuario');
      const fechaNacimiento = document.getElementById('fechaNacimientoUsuario');
      const correo = document.getElementById('correoUsuario');

      const nombreError = document.getElementById('nombreError');
      const apellidosError = document.getElementById('apellidosError');
      const fechaError = document.getElementById('fechaError');
      const emailError = document.getElementById('emailError');

      nombreError.classList.add('hidden');
      apellidosError.classList.add('hidden');
      fechaError.classList.add('hidden');
      emailError.classList.add('hidden');

      if (nombre.value.trim() === '') {
        nombreError.textContent = 'El nombre es obligatorio';
        nombreError.classList.remove('hidden');
        valid = false;
      }
      if (apellidos.value.trim() === '') {
        apellidosError.textContent = 'Los apellidos son obligatorios';
        apellidosError.classList.remove('hidden');
        valid = false;
      }
      if (fechaNacimiento.value.trim() === '') {
        fechaError.textContent = 'La fecha de nacimiento es obligatoria';
        fechaError.classList.remove('hidden');
        valid = false;
      }
      if (correo.value.trim() === '') {
        emailError.textContent = 'El correo es obligatorio';
        emailError.classList.remove('hidden');
        valid = false;
      } else if (!correo.value.includes('@')) {
        emailError.textContent = 'El correo debe contener @';
        emailError.classList.remove('hidden');
        valid = false;
      }

      if (!valid) {
        return;
      }

      submitButton.disabled = true;
      loader.classList.remove('hidden');

      const formData = new FormData(form);

      fetch(form.action, {
        method: form.method,
        body: formData
      })
      .then(response => {
        if(response.ok) {
          return response.json().then(data => ({
            status: 'success',
            message: data.mensaje || 'Guardado exitosamente'
          })).catch(() => ({
            status: 'success',
            message: 'Guardado exitosamente'
          }));
        } else {
          return response.json().then(data => ({
            status: 'error',
            message: data.mensaje || 'Error al guardar'
          })).catch(() => ({
            status: 'error',
            message: 'Error al guardar'
          }));
        }
      })
      .then(result => {
        loader.classList.add('hidden');
        showNotification(result.status, result.message);
      })
      .catch(error => {
        console.error("Error al enviar el formulario:", error);
        loader.classList.add('hidden');
        showNotification('error', 'Error al enviar el formulario');
      });
    });

    function showNotification(status, message) {
      const container = document.createElement('div');
      container.style.position = 'fixed';
      container.style.bottom = '20px';
      container.style.right = '20px';
      container.style.zIndex = '1000';

      let notificationHTML = "";
      if (status === 'success') {
          notificationHTML = `
              <div class="flex w-full max-w-sm overflow-hidden bg-white rounded-lg shadow-md dark:bg-gray-800">
                  <div class="flex items-center justify-center w-12 bg-emerald-500">
                      <svg class="w-6 h-6 text-white fill-current" viewBox="0 0 40 40" xmlns="http://www.w3.org/2000/svg">
                          <path d="M20 3.33331C10.8 3.33331 3.33337 10.8 3.33337 20C3.33337 29.2 10.8 36.6666 20 36.6666C29.2 36.6666 36.6667 29.2 36.6667 20C36.6667 10.8 29.2 3.33331 20 3.33331ZM16.6667 28.3333L8.33337 20L10.6834 17.65L16.6667 23.6166L29.3167 10.9666L31.6667 13.3333L16.6667 28.3333Z" />
                      </svg>
                  </div>
                  <div class="px-4 py-2 -mx-3">
                      <div class="mx-3">
                          <span class="font-semibold text-emerald-500 dark:text-emerald-400">Éxito</span>
                          <p class="text-sm text-gray-600 dark:text-gray-200">${message}</p>
                      </div>
                  </div>
              </div>
          `;
      } else if (status === 'error') {
          notificationHTML = `
              <div class="flex w-full max-w-sm overflow-hidden bg-white rounded-lg shadow-md dark:bg-gray-800">
                  <div class="flex items-center justify-center w-12 bg-red-500">
                      <svg class="w-6 h-6 text-white fill-current" viewBox="0 0 40 40" xmlns="http://www.w3.org/2000/svg">
                          <path d="M20 3.36667C10.8167 3.36667 3.3667 10.8167 3.3667 20C3.3667 29.1833 10.8167 36.6333 20 36.6333C29.1834 36.6333 36.6334 29.1833 36.6334 20C36.6334 10.8167 29.1834 3.36667 20 3.36667ZM19.1334 33.3333V22.9H13.3334L21.6667 6.66667V17.1H27.25L19.1334 33.3333Z" />
                      </svg>
                  </div>
                  <div class="px-4 py-2 -mx-3">
                      <div class="mx-3">
                          <span class="font-semibold text-red-500 dark:text-red-400">Error</span>
                          <p class="text-sm text-gray-600 dark:text-gray-200">${message}</p>
                      </div>
                  </div>
              </div>
          `;
      }
      container.innerHTML = notificationHTML;
      document.body.appendChild(container);

      setTimeout(() => {
        container.remove();
        window.location.href = "/VistaVoluntia/RolAdministrador/AdminNuevoUsuario.jsp";
      }, 3000);
    }
  });
  </script>

		<script>
  const loadFile = function(event) {
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