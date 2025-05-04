<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String token = request.getParameter("token");

if (token == null || token.isEmpty()) {
	String qs = request.getQueryString();
	if (qs != null) {
		String decodedQS = java.net.URLDecoder.decode(qs, "UTF-8");
		String[] params = decodedQS.split("&");
		for (String param : params) {
	if (param.startsWith("token=")) {
		token = param.substring("token=".length());
		break;
	}
		}
	}
}

if (token != null && token.startsWith("http://")) {
	try {
		java.net.URL urlObj = new java.net.URL(token);
		String query = urlObj.getQuery();
		if (query != null && query.startsWith("token=")) {
	token = query.substring("token=".length());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

if (token == null) {
	token = "";
}

System.out.println("Token obtenido: " + token);
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Voluntia</title>
<link rel="icon" type="image/x-icon" href="Imagenes/favicon.ico">

<link
	href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
	rel="stylesheet">

</head>

<body>
	<script src="https://cdn.tailwindcss.com"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script defer
		src="https://cdn.jsdelivr.net/npm/alpinejs@3.x.x/dist/cdn.min.js"></script>

	<section x-data="{ atTop: true }">
		<div
			class="fixed z-50 w-full px-8 py-4 transition-all duration-1000 rounded-full mt-4 max-w-2xl inset-x-0 mx-auto ease-in-out transform"
			:class="{ 'bg-black bg-opacity-90 backdrop-blur-xl max-w-4xl': !atTop, 'max-w-2xl': atTop }"
			@scroll.window="atTop = (window.pageYOffset > 50 ? false : true)">
			<div x-data="{ open: false }"
				class="flex flex-col w-full p-2 mx-auto md:items-center md:justify-between md:flex-row">
				<div class="flex flex-row items-center justify-between">
					<span class="font-bold tracking-tighter uppercase"
						:class="{ 'text-black': atTop, 'text-white': !atTop }">✺
						Voluntia</span>
					<button class="md:hidden focus:outline-none" @click="open = !open">
						<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6"
							fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2"
								d="M4 6h16M4 12h16M4 18h16" />
                        </svg>
					</button>
				</div>
				<nav :class="{'flex': open, 'hidden': !open}"
					class="flex-col flex-grow gap-8 hidden pb-4 md:pb-0 md:flex md:flex-row lg:ml-auto justify-end">
					<a :class="{ 'text-black': atTop, 'text-white': !atTop }"
						href="index.html">Inicio</a> <a
						:class="{ 'text-black': atTop, 'text-white': !atTop }"
						href="Nosotros.html">Nosotros</a> <a
						:class="{ 'text-black': atTop, 'text-white': !atTop }"
						href="ManualUsuario.html">Manual Usuario</a> <a
						:class="{ 'text-black': atTop, 'text-white': !atTop }"
						href="login.html">Acceso</a>
				</nav>
			</div>
		</div>

		<div class="bg-white">
			<div class="flex items-center justify-center min-h-screen pt-24">
				<div class="w-full max-w-md px-4">
					<h2 id="mainTitle"
						class="mb-6 text-center text-3xl font-extrabold text-gray-900">
						Establecer Nueva Contraseña</h2>
					<div id="loader"
						class="fixed inset-0 flex items-center justify-center bg-gray-700 bg-opacity-50 hidden z-50">
						<div
							class="w-16 h-16 border-4 border-indigo-500 border-dashed rounded-full animate-spin"></div>
					</div>
					<div id="formContainer"
						class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
						<form id="resetPasswordForm" action="/VistaVoluntia/login"
							method="post" class="space-y-6" novalidate>
							<input type="hidden" name="accion" value="recuperarPassword" />
							<input type="hidden" name="token" value="<%=token%>" />

							<div>
								<label for="newPassword"
									class="block text-sm font-medium text-gray-700">Nueva
									Contraseña</label>
								<div class="mt-1">
									<input id="newPassword" name="newPassword" type="password"
										required placeholder="Introduce la nueva contraseña"
										class="appearance-none rounded-md block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
								</div>
							</div>

							<div>
								<label for="confirmPassword"
									class="block text-sm font-medium text-gray-700">Confirmar
									Contraseña</label>
								<div class="mt-1">
									<input id="confirmPassword" name="confirmPassword"
										type="password" required
										placeholder="Confirma la nueva contraseña"
										class="appearance-none rounded-md block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
								</div>
								<p id="errorMessage" class="text-red-500 text-xs mt-1 hidden">Las
									contraseñas no coinciden.</p>
							</div>

							<div>
								<button id="submit-btn" type="submit"
									class="w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
									Establecer Contraseña</button>
							</div>

							<p id="resultMessage" class="mt-4 text-center hidden text-sm"></p>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script>
document.getElementById('resetPasswordForm').addEventListener('submit', async function(e) {
  e.preventDefault();
  const newPwd = document.getElementById('newPassword').value.trim();
  const confPwd = document.getElementById('confirmPassword').value.trim();
  const err = document.getElementById('errorMessage');
  const res = document.getElementById('resultMessage');
  err.classList.add('hidden');
  res.classList.add('hidden');
  if (!newPwd || !confPwd) {
    err.textContent = 'Ambos campos son obligatorios.';
    err.classList.remove('hidden');
    return;
  }
  if (newPwd !== confPwd) {
    err.textContent = 'Las contraseñas no coinciden.';
    err.classList.remove('hidden');
    return;
  }
  document.getElementById('loader').classList.remove('hidden');
  document.getElementById('formContainer').classList.add('opacity-50','pointer-events-none');
  try {
    await fetch(this.action, {
      method: this.method,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams(new FormData(this)).toString()
    });
    res.textContent = 'Contraseña restablecida exitosamente.';
    res.classList.remove('hidden','text-red-500');
    res.classList.add('text-green-500');
  } catch {
    err.textContent = 'Se ha producido un error. Intente nuevamente.';
    err.classList.remove('hidden');
  } finally {
    document.getElementById('loader').classList.add('hidden');
    document.getElementById('formContainer').classList.remove('opacity-50','pointer-events-none');
  }
});
</script>

</body>

</html>