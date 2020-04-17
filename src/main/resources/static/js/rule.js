function HideOrShowFont(obj) {
    if (obj == "a1") {
        document.getElementById("a1").style.color = "#FF5251";
        document.getElementById("a2").style.color = "#333";
        document.getElementById("a3").style.color = "#333";

    }

    else if (obj == 'a2') {

        document.getElementById("a1").style.color = "#333";
        document.getElementById("a2").style.color = "#FF5251";
        document.getElementById("a3").style.color = "#333";
    }
    else if (obj == 'a3') {
        document.getElementById("a1").style.color = "#333";
        document.getElementById("a2").style.color = "#333";
        document.getElementById("a3").style.color = "#FF5251";
    }
}
