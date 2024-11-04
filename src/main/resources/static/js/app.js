//const app = (() => {
//    var nickname = "";
//    var playerId = "";
//    var api = apiClient;
//
//    const login = async (newNickname) => {
//        nickname = newNickname;
//        try {
//            const response = await api.createPlayer(nickname);
//            playerId = response.id;
//            console.log("Player created" + nickname);
//            console.log(nickname.length);
//            window.location.href = "../templates/salas.html";
//        } catch (error) {
//            console.error("Error al crear el jugador", error);
//            alert(error.message);
//        }
//    };
//    const displayPlayerCount = async () => {
//        try {
//            const count = await api.getPlayerCount();
//            console.log(`Total de jugadores: ${count}`);
//        } catch (error) {
//            console.error("Error al obtener el conteo de jugadores", error);
//        }
//    };
//    return {
//        login
//    };
//
//})();