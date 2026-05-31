#!/usr/bin/env python3
"""
iFlyCode Agent Dynamic Test — WebSocket client that simulates the IDE plugin.
Sends commands to the Agent and records its responses.
"""
import asyncio
import json
import sys
import time
import uuid

try:
    import websockets
except ImportError:
    print("Install websockets: pip3 install websockets")
    sys.exit(1)

HOST = "127.0.0.1"
PORT = int(sys.argv[1]) if len(sys.argv) > 1 else 3597
URI = f"ws://{HOST}:{PORT}/ws/idea"

async def send_and_recv(ws, cmd_name, data=None, timeout=10):
    """Send a WebSocket message and wait for response."""
    msg_id = str(uuid.uuid4())[:8]
    payload = {
        "id": msg_id,
        "command": cmd_name,
        "data": data or {},
        "timeStamp": int(time.time() * 1000)
    }

    print(f"\n{'='*60}")
    print(f"[SEND] command={cmd_name}, id={msg_id}")
    print(f"[SEND] data={json.dumps(data)[:200]}")
    print(f"{'='*60}")

    await ws.send(json.dumps(payload))

    # Wait for responses (could be multiple streaming responses)
    responses = []
    try:
        while True:
            resp = await asyncio.wait_for(ws.recv(), timeout=timeout)
            try:
                obj = json.loads(resp)
                responses.append(obj)
                print(f"[RECV] type={obj.get('type','?')}, cmd={obj.get('command','?')}")
                # Show a snippet
                data_str = json.dumps(obj, ensure_ascii=False)
                if len(data_str) > 300:
                    print(f"[RECV] data={data_str[:300]}...")
                else:
                    print(f"[RECV] data={data_str}")
            except json.JSONDecodeError:
                # Check if SSE or binary
                if resp.startswith("data:"):
                    print(f"[SSE] {resp[:200]}")
                else:
                    print(f"[RAW] {resp[:200]}")

            # Check for end-of-stream markers
            if obj.get("type") == "end" or obj.get("command") == "end":
                break

    except asyncio.TimeoutError:
        print(f"[TIMEOUT] No response within {timeout}s")

    return responses

async def test_agent():
    print(f"Connecting to {URI} ...")
    try:
        async with websockets.connect(URI, max_size=10*1024*1024) as ws:
            print(f"✅ Connected! Agent ready.")

            # Test 1: Get version / simple status
            print(f"\n{'#'*60}")
            print(f"# TEST 1: USER_VERSION (status check)")
            print(f"{'#'*60}")
            await send_and_recv(ws, "user_version")

            # Test 2: Try a command the agent handles locally
            print(f"\n{'#'*60}")
            print(f"# TEST 2: USER_PERMISSION (local check)")
            print(f"{'#'*60}")
            await send_and_recv(ws, "user_permission")

            # Test 3: Send a simple chat command (will fail to connect to cloud,
            # but we should see the request being formed)
            print(f"\n{'#'*60}")
            print(f"# TEST 3: TALK_INTELLIGENT (chat message)")
            print(f"{'#'*60}")
            await send_and_recv(ws, "talk_intelligent", {
                "inputText": "Hello",
                "sessionId": str(uuid.uuid4()),
                "type": "TALK:INTELLIGENT"
            })

    except websockets.exceptions.WebSocketException as e:
        print(f"❌ Connection failed: {e}")
        print("   Make sure the Agent is running on port 3597")
        return

if __name__ == "__main__":
    asyncio.run(test_agent())
