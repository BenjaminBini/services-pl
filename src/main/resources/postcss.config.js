const purgecss = require('@fullhuman/postcss-purgecss')({

    // Specify the paths to all of the template files in your project
    content: [
      './templates/**/*.jade',
    ],

    // Include any special characters you're using in this regular expression
    defaultExtractor: content => purgeFromPug(content) || []
  })

  module.exports = {
    plugins: [
        require('tailwindcss'),
        require('autoprefixer'),
        purgecss,
        require('cssnano')({
            preset: 'default',
        }),
    ]
  }

  
const purgeFromPug = (content) => {
    const tokens = require('pug-lexer')(content);
    var selectors = [];
    for (const token of tokens) {
      switch (token.type) {
        case "tag":
        case "id":
        case "class":
          selectors.push(token.val);
          break;
        case "attribute":
          if (token.name === "class" || token.name === "id") {
            token.val = token.mustEscape ? token.val.replace(/"/g, "") : token.val
            const classes = token.val.split(" ");
            selectors = [...selectors, ...classes];
          }
          break;
        default:
          break;
      }
    }
    return selectors;
  };