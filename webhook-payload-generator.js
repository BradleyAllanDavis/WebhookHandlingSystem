const crypto = require('crypto');

function flattenObject(obj) {
    let flatArray = [];

    function flatten(obj, parentKey = '') {
        for (let key in obj) {
            if (obj.hasOwnProperty(key)) {
                let newKey = parentKey ? `${parentKey}.${key}` : key;
                if (typeof obj[key] === 'object' && obj[key] !== null) {
                    flatten(obj[key], newKey);
                } else {
                    flatArray.push({
                        key: newKey,
                        value: obj[key]
                    });
                }
            }
        }
    }

    flatten(obj);

    // Sort flatArray alphabetically by keys
    flatArray.sort((a, b) => a.key.localeCompare(b.key));

    return flatArray;
}

function getStringToSign(obj) {
    const flatArray = flattenObject(obj);
    return flatArray.map(item => item.key + ":" + JSON.stringify(item.value)).join(';');
}

function prepareWebhookPayload(data, secretKey) {
    const hmac = crypto.createHmac('sha256', secretKey);
    const string = getStringToSign(data)
    hmac.update(string);
    const signature = hmac.digest('hex');
    return {
        signature,
        data
    };
}

// Example usage:
const obj = {
    a: {
        b: 1,
        y: "foo",
        a: null,
        c: {
            d: 2,
            e: {
                f: 3
            }
        }
    },
    g: 4
};

const webhook_data1 = {
    id: "2d385ccc-18dd-4944-977a-b803092c5302",
    type: "deposit",
    asset: "USD",
    cents: "100001",
    status: "settled",
    updated_at: 1706598225
};

const webhook_data2 = {
    id: "2d385ccc-18dd-4944-977a-b803092c5302",
    type: "deposit",
    asset: "USD",
    cents: "100001",
    status: "pending",
    updated_at: 1706598215
};

const webhook_data4 = {
    id: "2d385ccc-18dd-4944-977a-b803092c5302",
    type: "deposit",
    asset: "USD",
    cents: "100001",
    status: "reversed",
    updated_at: 1706598240
};

const secretKey = 'my_secret_key';
// const payload = prepareWebhookPayload(obj, secretKey);
// const payload = prepareWebhookPayload(webhook_data1, secretKey);
// const payload = prepareWebhookPayload(webhook_data2, secretKey);
const payload = prepareWebhookPayload(webhook_data4, secretKey);
console.log(JSON.stringify(payload, null, 2));
