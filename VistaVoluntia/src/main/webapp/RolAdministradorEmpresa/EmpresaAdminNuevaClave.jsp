<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&amp;display=swap"
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
                            <div class="shrink-0">

                            </div>
                            <div class="hidden md:block">
                                <div class="ml-10 flex items-baseline space-x-4">
                                    <div>
                                        <a href="../RolAdministradorEmpresa/PanelAdministradorEmpresa.html"
                                            class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Inicio</a>
                                    </div>
                                    <div class="relative">
                                        <a href="#"
                                            class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Usuario</a>
                                        <div
                                            class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminNuevoUsuario.jsp"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                                usuario</a>
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminTablaUsuarios.html"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                                usuarios</a>
                                        </div>
                                    </div>
                                    <div class="relative">
                                        <a href="#"
                                            class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Servicios</a>
                                        <div
                                            class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminNuevoServicio.jsp"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                                servicio</a>
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminTablaServicios.html"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                                servicios</a>
                                        </div>
                                    </div>
                                    <div class="relative">
                                        <a href="#"
                                            class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Emergencias</a>
                                        <div
                                            class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
                                                emergencia</a>
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminTablaEmergencias.html"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                                emergencias</a>
                                        </div>
                                    </div>
                                    <div class="relative">
                                        <a href="#"
                                            class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Vehiculos</a>
                                        <div
                                            class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminNuevoVehiculo.jsp"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                                vehiculo</a>
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminTablaVehiculos.html"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                                vehiculos</a>
                                        </div>
                                    </div>
                                    <div class="relative">
                                        <a href="#"
                                            class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Organizaciones</a>
                                        <div
                                            class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminNuevaOrganizacion.jsp"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
                                                Organizacion</a>
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminTablaOrganizaciones.html"
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
                                                plan</a>
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminTablaPlanes.html"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                                planes</a>
                                        </div>
                                    </div>
                                    <div class="relative">
                                        <a href="#"
                                            class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Claves</a>
                                        <div
                                            class="absolute left-0 hidden mt-2 space-y-1 bg-gray-800 rounded-md w-40 z-10">
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminNuevaClave.jsp"
                                                class="block px-3 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
                                                clave</a>
                                            <a href="../RolAdministradorEmpresa/EmpresaAdminTablaClaves.html"
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
                                            id="user-menu-button" aria-expanded="false" aria-haspopup="true">
                                            <span class="absolute -inset-1.5"></span>
                                            <span class="sr-only">Open user menu</span>
                                            <img class="size-8 rounded-full" id="imagenUsuarioPerfil"
                                                src="https://us.123rf.com/450wm/thesomeday123/thesomeday1231712/thesomeday123171200009/91087331-icono-de-perfil-de-avatar-predeterminado-para-hombre-marcador-de-posici%C3%B3n-de-foto-gris-vector-de.jpg?ver=6"
                                                alt="Imagen de Perfil del usuario">
                                        </button>
                                    </div>

                                    <div class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 ring-1 shadow-lg ring-black/5 focus:outline-hidden"
                                        role="menu" aria-orientation="vertical" aria-labelledby="user-menu-button"
                                        tabindex="-1">
                                        <a href="../RolAdministradorEmpresa/EmpresaAdminPerfilUsuario.jsp"
                                            class="block px-4 py-2 text-sm text-gray-700" role="menuitem" tabindex="-1"
                                            id="user-menu-item-0">Perfil</a>
                                        <a href="#" class="block px-4 py-2 text-sm text-gray-700" role="menuitem"
                                            tabindex="-1" id="user-menu-item-2">Cerrar</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="-mr-2 flex md:hidden">
                            <button type="button"
                                class="relative inline-flex items-center justify-center rounded-md bg-gray-800 p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 focus:outline-hidden"
                                aria-controls="mobile-menu" aria-expanded="false">
                                <span class="absolute -inset-0.5"></span>
                                <span class="sr-only">Open main menu</span>
                                <svg class="block size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                    stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                                </svg>
                                <svg class="hidden size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                    stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="md:hidden" id="mobile-menu">
                    <div class="space-y-1 px-2 pt-2 pb-3 sm:px-3">
                        <div>
                            <a href="../RolAdministradorEmpresa/PanelAdministradorEmpresa.html"
                                class="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white">Inicio</a>
                        </div>
                        <div class="relative">
                            <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-white"
                                aria-current="page" id="dashboard-menu">Usuarios</a>
                            <div class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2 z-50"
                                id="dashboard-submenu">
                                <a href="../RolAdministradorEmpresa/EmpresaAdminNuevoUsuario.jsp"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                    usuario</a>
                                <a href="../RolAdministradorEmpresa/EmpresaAdminTablaUsuarios.html"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                    usuarios</a>
                            </div>

                        </div>
                        <div class="relative">
                            <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-white"
                                aria-current="page" id="dashboard-menu">Servicios</a>
                            <div class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
                                id="dashboard-submenu">
                                <a href="../RolAdministradorEmpresa/EmpresaAdminNuevoServicio.jsp"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                    servicio</a>
                                <a href="../RolAdministradorEmpresa/EmpresaAdminTablaServicios.html"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                    servicios</a>
                            </div>
                        </div>
                        <div class="relative">
                            <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-white"
                                aria-current="page" id="dashboard-menu">Emergencias</a>
                            <div class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
                                id="dashboard-submenu">
                                <a href="../RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                    emergencias</a>
                                <a href="../RolAdministradorEmpresa/EmpresaAdminTablaEmergencias.html"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                    emergencias</a>
                            </div>
                        </div>
                        <div class="relative">
                            <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-white"
                                aria-current="page" id="dashboard-menu">Vehiculos</a>
                            <div class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
                                id="dashboard-submenu">
                                <a href="../RolAdministradorEmpresa/EmpresaAdminNuevoVehiculo.jsp"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                    vehiculo</a>
                                <a href="../RolAdministradorEmpresa/EmpresaAdminTablaVehiculos.html"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                    vehiculos</a>
                            </div>
                        </div>
                        <div class="relative">
                            <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-white"
                                aria-current="page" id="dashboard-menu">Organizaciones</a>
                            <div class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
                                id="dashboard-submenu">
                                <a href="../RolAdministradorEmpresa/EmpresaAdminNuevaOrganizacion.jsp"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
                                    organizacion</a>
                                <a href="../RolAdministradorEmpresa/EmpresaAdminTablaOrganizaciones.html"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                    organizaciones</a>
                            </div>
                        </div>
                        <div class="relative">
                            <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-white"
                                aria-current="page" id="dashboard-menu">Planes</a>
                            <div class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
                                id="dashboard-submenu">
                                <a href="../RolAdministradorEmpresa/EmpresaAdminNuevoPlan.jsp"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nuevo
                                    plan</a>
                                <a href="../RolAdministradorEmpresa/EmpresaAdminTablaPlanes.html"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                    planes</a>
                            </div>
                        </div>
                        <div class="relative">
                            <a href="#" class="block rounded-md px-3 py-2 text-base font-medium text-white"
                                aria-current="page" id="dashboard-menu">Claves</a>
                            <div class="absolute left-0 hidden space-y-1 mt-1 bg-gray-800 rounded-md shadow-lg p-2"
                                id="dashboard-submenu">
                                <a href="#"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Nueva
                                    clave</a>
                                <a href="#"
                                    class="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white">Tabla
                                    claves</a>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
            <header class="bg-white shadow-sm">
                <div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8 flex justify-between items-center">
                    <h1 class="text-3xl font-bold tracking-tight text-gray-900">Nueva Clave</h1>
                    <a href="../RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp"
                        class="inline-flex items-center px-6 py-3 text-lg font-semibold text-white bg-red-600 rounded-lg shadow-lg hover:bg-red-700 focus:outline-none focus:ring-4 focus:ring-red-400 transition">
                        <svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"
                            xmlns="http://www.w3.org/2000/svg">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="M12 8v4m0 4h.01M4.93 19h14.14a2 2 0 001.73-3L13.73 4a2 2 0 00-3.46 0L3.2 16a2 2 0 001.73 3z">
                            </path>
                        </svg>
                        Lanzar Emergencia
                    </a>
                </div>
            </header>
            <main>
                <div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                        <x-placeholder>
                            <div class="mx-auto w-full bg-white p-6 shadow rounded-xl">

                                <form id="miFormulario" class="needs-validation" novalidate
                                    action="/VistaVoluntia/clave" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="accion" value="aniadir" />

                                    <div class="-mx-3 flex flex-wrap">
                                        <div class="w-full px-3 sm:w-1/2">
                                            <div class="mb-5">
                                                <label for="nombreClave"
                                                    class="mb-3 block text-base font-medium text-[#07074D]">
                                                    Nombre Clave
                                                </label>
                                                <input type="text" name="nombreClave" id="nombreClave"
                                                    placeholder="Nombre Clave"
                                                    class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                                                <p class="text-red-500 text-sm mt-1 hidden" id="errorNombreClave">Este
                                                    campo es obligatorio.</p>
                                            </div>
                                        </div>

                                        <div class="w-full px-3 sm:w-1/2">
                                            <div class="mb-5">
                                                <label for="descripcionClave"
                                                    class="mb-3 block text-base font-medium text-[#07074D]">
                                                    Descripcion Clave
                                                </label>
                                                <input type="text" name="descripcionClave" id="descripcionClave"
                                                    required placeholder="Descripcion Clave"
                                                    class="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md" />
                                                <p class="text-red-500 text-sm mt-1 hidden" id="errorDescripcionClave">
                                                    Este campo es obligatorio.</p>
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


                                <div id="loader"
                                    class="fixed inset-0 flex items-center justify-center bg-gray-700 bg-opacity-50 hidden z-50">
                                    <div
                                        class="w-16 h-16 border-4 border-blue-500 border-dashed rounded-full animate-spin">
                                    </div>
                                </div>

                            </div>
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
            });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', () => {
                const status = "<%= request.getAttribute("status") %>";
                const mensaje = "<%= request.getAttribute("mensaje") %>";

                if (mensaje) {
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
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
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
            document.addEventListener('DOMContentLoaded', function () {
                const form = document.getElementById('miFormulario');
                const submitButton = form.querySelector('button[type="submit"]');

                form.addEventListener('submit', function (e) {
                    e.preventDefault();

                    document.getElementById('errorNombreClave').classList.add('hidden');
                    document.getElementById('errorDescripcionClave').classList.add('hidden');

                    let valid = true;

                    const inputNombreClave = document.getElementById('nombreClave');
                    const inputDescripcionClave = document.getElementById('descripcionClave');

                    if (!inputNombreClave.value.trim()) {
                        valid = false;
                        document.getElementById('errorNombreClave').classList.remove('hidden');
                    }

                    if (!inputDescripcionClave.value.trim()) {
                        valid = false;
                        document.getElementById('errorDescripcionClave').classList.remove('hidden');
                    }

                    if (!valid) {
                        return;
                    }

                    submitButton.disabled = true;
                    document.getElementById('loader').classList.remove('hidden');

                    const formData = new FormData(form);
                    fetch(form.action, {
                        method: form.method,
                        body: formData
                    })
                        .then(response => {
                            if (response.ok) {
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
                            document.getElementById('loader').classList.add('hidden');
                            showNotification(result.status, result.message);
                        })
                        .catch(error => {
                            console.error("Error al enviar el formulario:", error);
                            document.getElementById('loader').classList.add('hidden');
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
                        window.location.href = "/VistaVoluntia/RolAdministradorEmpresa/EmpresaAdminNuevaClave.jsp";
                    }, 3000);
                }
            });
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