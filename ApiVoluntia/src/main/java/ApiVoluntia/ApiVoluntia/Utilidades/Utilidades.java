package ApiVoluntia.ApiVoluntia.Utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class Utilidades {

	public static void ficheroLog(int nivelLog, String mensajeLog) {
		LocalDate fecha = LocalDate.now();
		String carpetaLogs = "C:\\Users\\david\\Desktop\\Fichero Log\\VistaLog\\";
		String ficheroLogRuta = carpetaLogs + "logApi-" + fecha + ".txt";

		File directorio = new File(carpetaLogs);
		if (!directorio.exists()) {
			if (!directorio.mkdirs()) {
				System.out.println("[ALERTA] -> No se pudo crear la carpeta de logs.");
				return;
			}
		}

		String prefijoLog;
		switch (nivelLog) {
		case 1:
			prefijoLog = "[INFO] -> ";
			break;
		case 2:
			prefijoLog = "[WARN] -> ";
			break;
		case 3:
			prefijoLog = "[ERROR] -> ";
			break;
		default:
			System.out.println("[ALERTA] -> Nivel de log no válido.");
			return;
		}

		try (FileWriter escribir = new FileWriter(ficheroLogRuta, true)) {
			escribir.write(prefijoLog + mensajeLog + "\n");
		} catch (IOException e) {
			System.out.println("[ALERTA] -> Error al escribir en el log: " + e.getMessage());
		}
	}
}
