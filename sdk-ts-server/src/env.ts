import dotenv from "dotenv-safe";
import * as path from "path";

dotenv.config({ path: path.resolve(process.env.ENV_PATH || ".env") });
