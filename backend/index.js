const express = require('express');
const cors = require('cors');
const app = express();
const port = 5000;

// Enable cross-origin requests
app.use(cors());
app.use(express.json());

// Example API
app.get('/api/hello', (req, res) => {
  res.json({ message: 'Hello from Express!' });
});

app.listen(port, () => {
  console.log(`🚀 Backend running at http://localhost:${port}`);
});

