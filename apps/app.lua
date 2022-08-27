local e = require "evdev"
local fakeMouse = e.Uinput "/dev/uinput"
-- register supported events
fakeMouse:useEvent(e.EV_KEY)
fakeMouse:useEvent(e.EV_REL)
fakeMouse:useKey(e.BTN_0)
fakeMouse:useKey(e.BTN_1)
fakeMouse:useRelAxis(e.REL_X,-100,100)
fakeMouse:useRelAxis(e.REL_Y,-100,100)
fakeMouse:init()
-- inject a few events; mouse move, button press, button release
fakeMouse:write(e.EV_REL, e.REL_X, -50)
fakeMouse:write(e.EV_REL, e.REL_Y, 0)
fakeMouse:write(e.EV_SYN, e.SYN_REPORT, 0)
fakeMouse:write(e.EV_KEY, e.BTN_0, 1)
fakeMouse:write(e.EV_SYN, e.SYN_REPORT, 0)
fakeMouse:write(e.EV_KEY, e.BTN_0, 0)
fakeMouse:write(e.EV_SYN, e.SYN_REPORT, 0)
-- dispose
fakeMouse:close()
