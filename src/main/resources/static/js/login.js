const login = (() => {
    let nickname = "";
    let api = apiClient;

    const login = async (newNickname) => {
        localStorage.clear();
        nickname = newNickname;
        console.log("Nickname:", nickname);
        try {
            await api.login(nickname);
            console.log("Player created" + nickname);
            localStorage.setItem('nickname', nickname);
            window.location.href = "../templates/sessionMenu.html";
        } catch (error) {
            alert("El nickname ya existe, por favor ingrese otro");
        }
    };

    return {
        login, getNickname: () => nickname
    };

})();