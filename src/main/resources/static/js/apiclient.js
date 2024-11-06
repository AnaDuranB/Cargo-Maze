/*
await se utiliza para esperar a que la promesa devuelta por fetch
se resuelva, es decir, esperar a que los datos lleguen del servidor.
*/

const apiClient = (() => {

    const url = "http://localhost:8080/cargoMaze/";

    //GET

    const getGameSessionBoard = async (gameSessionId) =>  {
        try{
            let response = await fetch(`${url}sessions/${gameSessionId}/board/state`);
            let data = response.json;
            return data;
        }catch(error){
            console.error("Error searching for gameSession by id",error);
        }
    }

    const getPlayersInSession = async (gameSessionId) => {
        try {
            let response = await fetch(`${url}sessions/${gameSessionId}/players`);
            let data = response.json();
            return data;
        } catch (error) {
            console.error("Error retrieving players in session:", error);
        }
    };

    const getPlayerCountInSession = async (gameSessionId) => {
        try {
            let response = await fetch(`${url}sessions/${gameSessionId}/players/count`);
            return await response.json();
        } catch (error) {
            console.log("Error retrieving player count in session:", error);
        }
    };
    
    //POST

    const login = async (nickname) => {
        let json = JSON.stringify({nickname: nickname });
        let promise = $.ajax({
            url: url + "players",
            type: "POST",
            data: json,
            contentType: "application/json"
        })
        return promise;
    };

    //PUT
    
    const enterSession = async (gameSessionId, nickname) => {
        let json = JSON.stringify({ nickname: nickname });

        try {
            let response = await $.ajax({
                url: url + "sessions/" + gameSessionId + "/players",
                type: 'PUT',
                data: json,
                contentType: "application/json"
            });
            console.log(response); // Log successful response
            return response; // Return the response to the caller
        } catch (error) {
            console.error(`Error entering session: ${error.responseText || error.message}`);
            // Optionally handle error UI here
            throw error; // Rethrow the error for further handling if needed
        }
    };

    
    const movePlayer = async (gameSessionId, nickname, newPosition) => {
        let json = JSON.stringify({"x": newPosition.x, "y": newPosition.y});
        console.log(json);
        try {
            let response = await $.ajax({
                url: url + "sessions/" + gameSessionId + "/players/" + nickname + "/move",
                type: 'PUT',
                data: json,
                contentType: "application/json"
            });
            return response; // Return the response to the caller
        } catch (error) {
            console.log(`Error moving: ${error.responseText || error.message}`);
        }
    };

    //DELETE

    const removePlayerFromSession = async (gameSessionId, nickname) => {
        let json = JSON.stringify({ nickname: nickname, gameSessionId: gameSessionId });
        try {
            let response = await $.ajax({
                url: url + "sessions/" + gameSessionId + "/players/" + nickname,
                type: 'DELETE',
                data: json,
                contentType: "application/json"
            });
            console.log(response); // Log successful response
            return response; // Return the response to the caller
        } catch (error) {
            console.error(`Error removing player from session: ${error.responseText || error.message}`);
        }
    }

    return {
        login,
        getGameSessionBoard,
        enterSession,
        getPlayersInSession,
        movePlayer,
        removePlayerFromSession,
        getPlayerCountInSession

    };

})();