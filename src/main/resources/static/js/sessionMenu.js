// login.js
import { getNickname } from './state.js';


const sessionMenu = (() => {
    var nickname = getNickname();
    var api = apiClient;

    const enterSession = async (sessionId) => {
        try {
            if(nickname.length == 0){
                alert("No se ha ingresado un nickname");
                return;
            }
            api.enterSession(sessionId, nickname);
            window.location.href = "../templates/game.html";
        } catch (error) {
            alert("No se pudo ingresar a la sesión"); //hay que implementar la validacion si el usuario se sale de la sesion
                                                      // se debe eliminar de la lista de jugadores.
        }
    };

    return {
        enterSession
    };

})();

window.sessionMenu = sessionMenu;