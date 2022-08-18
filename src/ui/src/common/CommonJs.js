const setComma = (number) => {
    return number.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")
}

const groupBy = function (data, key) {
    return data.reduce(function (carry, el) {
        let group = el[key];

        if (carry[group] === undefined) {
            carry[group] = []
        }

        carry[group].push(el)
        return carry
    }, {})
}

function hexToRgbA(hex, opacity) {
    let c;
    if (/^#([A-Fa-f0-9]{3}){1,2}$/.test(hex)) {
        c = hex.substring(1).split('');
        if (c.length === 3) {
            c = [c[0], c[0], c[1], c[1], c[2], c[2]];
        }
        c = '0x' + c.join('');

        if(opacity === undefined){
            opacity = 1
        }

        return 'rgba(' + [(c >> 16) & 255, (c >> 8) & 255, c & 255].join(',') + ',' + opacity + ')';
    }
    throw new Error('Bad Hex');
}

export {setComma, groupBy, hexToRgbA}
