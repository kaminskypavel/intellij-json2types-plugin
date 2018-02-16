import * as express from "express";
import {Request, Response} from "express";
import * as bodyParser from "body-parser";
import {IReqBody} from "./@types";
import {json2ts} from "json-ts";

const app = express();
app.use(bodyParser.urlencoded({extended: false}));

app.post('/', (req: Request, res: Response) => {
	const {json, type} = req.body as IReqBody;
	res.send(json2ts(json, {flow: type === 'flow'}));
});

app.listen(3000, () => console.log('server is running'));
