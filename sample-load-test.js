import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '1m', target: 150 }, // simulate ramp-up of traffic from 1 to 150 users over 1 minutes.
        { duration: '2m', target: 150 }, // stay at 150 users for 2 minutes
        { duration: '3m', target: 300 }, // ramp-up to 300 users over 3 minutes (peak hour starts)
        { duration: '2m', target: 300 }, // stay at 300 users for short amount of time (peak hour)
        { duration: '1m', target: 50 }, // ramp-down to 50 users over 1 minutes (peak hour ends)
    ],
    thresholds: {
        'http_req_duration': ['p(99)<1500'], // 99% of requests must complete below 1.5s
    },
};

const BASE_URL = 'http://localhost:8080/tutorials';

export default () => {
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const createdPayload = JSON.stringify({
        title: 'Tutorial',
        description: `A tutorial, in education, 
        is a method of transferring knowledge and may be used as a part of a learning process.`,
    })

    const createdResp = http.post(`${BASE_URL}`, createdPayload, params);

    check(createdResp, {
        'tutorial created in successfully': (resp) => resp.status === 201,
    });

    const data = createdResp.json()

    const changedPayload = JSON.stringify({
        title: 'Tutorial',
        description: `A tutorial, in education,
        is a method of transferring knowledge and may be used as a part of a learning process.
        More interactive and specific than a book or a lecture,
        a tutorial seeks to teach by example and supply the information to complete a certain task.`,
    })

    const changedResp = http.put(`${BASE_URL}/${data.id}`, changedPayload, params);

    check(changedResp, {
        'tutorial changed in successfully': (resp) => resp.status === 204,
    });

    const publishedResp = http.put(`${BASE_URL}/${data.id}/publish`, {}, params);

    check(publishedResp, {
        'tutorial published in successfully': (resp) => resp.status === 204,
    });

    const deletedResp = http.del(`${BASE_URL}/${data.id}`, {}, params);

    check(deletedResp, {
        'tutorial deleted in successfully': (resp) => resp.status === 204,
    });

    sleep(1);
};