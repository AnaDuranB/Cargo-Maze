const login = (() => {
    let nickname = "";
    let api = apiClient;


    document.addEventListener('DOMContentLoaded', function() {
        sessionStorage.clear();
    });

    const login = async (newNickname) => {
        sessionStorage.clear();
        nickname = newNickname;
        console.log("Nickname:", nickname);
        try {
            await api.login(nickname);
            sessionStorage.setItem('nickname', nickname);
            window.location.href = "../templates/sessionMenu.html";
        } catch (error) {
            alert("El nickname ya existe, por favor ingrese otro");
        }
    };

    return {
        login, getNickname: () => nickname
    };

})();