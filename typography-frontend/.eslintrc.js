module.exports = {
    env: {
        browser: true,
        es6: true
    },
    extends: [
        'plugin:react/recommended',
        'airbnb'
    ],
    globals: {
        Atomics: 'readonly',
        SharedArrayBuffer: 'readonly'
    },
    plugins: [
        'react'
    ],
    rules: {
        'comma-dangle': ['warn', 'never'],
        'linebreak-style': 'off',
        'react/jsx-filename-extension': [
            'error',
            {
                extensions: [
                    '.js',
                    '.jsx'
                ]
            }
        ],
        'react/jsx-props-no-spreading': 'off'
    }
};
